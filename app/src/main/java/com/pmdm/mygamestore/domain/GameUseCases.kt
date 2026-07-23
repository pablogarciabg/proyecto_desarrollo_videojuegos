package com.pmdm.mygamestore.domain

import com.pmdm.mygamestore.data.repository.GameRepository
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.flow.Flow

/**
 *  Casos de uso agrupados para operaciones con juegos
 *
 * PATRÓN USE CASE:
 * - Encapsula lógica de negocio específica de la aplicación
 * - Orquesta llamadas a uno o más repositories
 * - Transforma datos del dominio para casos de uso específicos
 * - Es independiente del framework (Android, iOS, Web)
 *
 * ORGANIZACIÓN:
 * ✅ Una clase por entidad/funcionalidad (GameUseCases, LibraryUseCases, etc.)
 * ✅ Cada método es un caso de uso concreto
 * ✅ NO usamos operator invoke() (llamada directa al método)
 * ✅ Preparado para inyección de dependencias con Koin
 *
 * MANEJO DE RESOURCE:
 * ✅ Recibe Flow<Resource<List<Game>>> del repository
 * ✅ Aplica lógica solo a Resource.Success
 * ✅ Propaga Loading y Error sin modificar
 * ✅ Devuelve Flow<Resource<List<Game>>> al ViewModel
 *
 * @property gamesRepository Repository para acceder a los datos de juegos
 */
class GameUseCases(
    private val gamesRepository: GameRepository
) {
    /**
     * Caso de uso para obtener juegos filtrados.
     * Recibe los filtros, se los envía al repository y devuelve el Flow con el Resource.
     */
    fun getFilteredGames(
        query: String = "",
        category: GameCategory = GameCategory.ALL,
        platform: PlatformEnum = PlatformEnum.ALL,
        interval: DateInterval = DateInterval.ALL_TIME
    ): Flow<Resource<List<Game>>> {
        return gamesRepository.getFilteredGames(
            query = query,
            category = category,
            platform = platform,
            interval = interval
        )
    }

    /**
     * Obtiene el catálogo completo de juegos sin ningún filtro aplicado.
     */
    fun getAllGames(): Flow<Resource<List<Game>>> {
        return gamesRepository.getAllGames()
    }

    /**
     * Obtiene la información detallada de un juego específico a través de su identificador.
     */
    suspend fun getGameBy(id: Int): Resource<Game> {
        return gamesRepository.getGameBy(id)
    }

    suspend fun removeFavorite(gameId: Int) {
        return gamesRepository.removeFavorite(gameId)
    }

    suspend fun addFavorite(gameId: Int) {
        return gamesRepository.addFavorite(gameId)
    }

    suspend fun isFavorite(gameId: Int): Boolean {
        return gamesRepository.isFavorite(gameId)
    }
}