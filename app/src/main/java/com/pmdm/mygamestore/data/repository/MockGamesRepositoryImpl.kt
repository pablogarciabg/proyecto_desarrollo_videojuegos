package com.pmdm.mygamestore.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.pmdm.mygamestore.data.local.AppDataBase
import com.pmdm.mygamestore.data.local.MockDataSource
import com.pmdm.mygamestore.data.local.SessionManagerImpl
import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import com.pmdm.mygamestore.data.mapper.toDomain
import com.pmdm.mygamestore.domain.model.AppError
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.GameNote
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 *  Implementación MOCK del repositorio de juegos
 *
 * PROPÓSITO:
 * - Desarrollo sin depender de backend/API
 * - Testing con datos controlados
 * - Simular comportamiento de API real
 *
 * CARACTERÍSTICAS:
 * ✅ Filtra datos en MEMORIA (no en servidor)
 * ✅ Simula delays de red para testing realista
 * ✅ Devuelve Resource (Loading → Success/Error)
 * ✅ Maneja errores con try-catch
 *
 * IMPORTANTE - Filtros locales:
 * Esta implementación filtra MockGamesDataSource en el DISPOSITIVO.
 * En una API real, los filtros se ejecutarían en el BACKEND.
 *
 * Ejemplo de diferencia:
 */
class MockGamesRepositoryImpl(
    private val context: Context,
    private val db: AppDataBase
) : GameRepository {
    private val dataSource = MockDataSource
    private val sessionManager = SessionManagerImpl(context)

    //Guardamos los ids de los juegos que marcamos como favoritos
    private val favoriteIds = mutableSetOf<Int>()

    private suspend fun simulateNetworkDelay() {
        delay(800)
    }

    override fun getAllGames(): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()
            emit(Resource.Success(dataSource.games))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Unknown Error")))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFilteredGames(
        query: String,
        category: GameCategory,
        platform: PlatformEnum,
        interval: DateInterval
    ): Flow<Resource<List<Game>>> = flow {
        try {
            emit(Resource.Loading)
            simulateNetworkDelay()

            var filtered = dataSource.games

            //Filtro por query
            if(query.isNotBlank()) {
                filtered = filtered.filter { game ->
                    game.title.contains(query, ignoreCase = true) ||
                            game.description.contains(query, ignoreCase = true)
                }
            }

            //Filtro por categoria
            if(category != GameCategory.ALL) {
                filtered = filtered.filter { it.category == category }
            }

            //Filtro por Plataforma
            if(platform != PlatformEnum.ALL) {
                filtered = filtered.filter { game ->
                    game.platforms.any { p ->
                        when(platform) {
                            PlatformEnum.PC -> p.slug.contains("pc", ignoreCase = true)
                            PlatformEnum.PLAYSTATION -> p.slug.contains("playstation", ignoreCase = true)
                            PlatformEnum.XBOX -> p.slug.contains("xbox", ignoreCase = true)
                            PlatformEnum.NINTENDO -> p.slug.contains("nintendo", ignoreCase = true)
                            PlatformEnum.MOBILE -> p.slug.contains("android", ignoreCase = true) ||
                                    p.slug.contains("ios", ignoreCase = true)
                            else -> false
                        }
                    }
                }
            }

            //Filtro por Intervalo
            if(interval != DateInterval.ALL_TIME) {
                val now = LocalDate.now()
                filtered = filtered.filter {
                    val gameDate = LocalDate.parse(it.releaseDate, DateTimeFormatter.ISO_DATE)
                    when(interval) {
                        DateInterval.LAST_WEEK -> gameDate.isAfter(now.minusWeeks(1))
                        DateInterval.LAST_30_DAYS -> gameDate.isAfter(now.minusDays(30))
                        DateInterval.LAST_90_DAYS -> gameDate.isAfter(now.minusDays(90))
                        else -> true
                    }
                }
            }
            emit(Resource.Success(filtered))
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e.message ?: "Error filtering games")))
        }
    }

    override suspend fun getGameBy(id: Int): Resource<Game> {
        return try {
            simulateNetworkDelay()

            val game = dataSource.games.find { it.id == id }

            if(game != null) {
                Resource.Success(game)
            } else {
                Resource.Error(AppError.NotFound)
            }
        } catch (e: Exception) {
            Resource.Error(AppError.Unknown(e.message ?: "Error getting game"))
        }
    }

    override suspend fun isFavorite(gameId: Int): Boolean {
        return favoriteIds.contains(gameId)
    }

    override suspend fun addFavorite(gameId: Int) {
        favoriteIds.add(gameId)
    }

    override suspend fun removeFavorite(gameId: Int) {
        favoriteIds.remove(gameId)
    }

    /**
     * @return El nombre de usuario de la sesion actual
     */
    private suspend fun getCurrentUser(): String {
        return sessionManager.getUserName().first()
            ?: throw IllegalStateException("No hay ususario con sesion iniciada")
    }

    override fun getFavoriteGames(): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading)
        val user = getCurrentUser()
        //1. Escuchamos cambios en Room(DAO)
        db.libraryDao().getGameByStatus(user, "FAVORITE").collect { entities ->
            //2. Cruzamos los IDs de Room con los datos del MockDataSource
            val games = entities.mapNotNull { entity ->
                dataSource.games.find { it.id == entity.gameId }
            }
            //3. Emitimos el resultado al final del UseCase
            emit(Resource.Success(games))
        }
    }

    override suspend fun getNote(gameId: Int): GameNote? {
        val user = getCurrentUser()
        val entity = db.gameNoteDao().getNote(gameId, user).firstOrNull()
        return entity?.toDomain()
    }
}