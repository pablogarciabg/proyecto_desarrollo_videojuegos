package com.pmdm.mygamestore.data.repository

import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.GameNote
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.flow.Flow

/**
 *  Interfaz que define el contrato del repositorio de juegos
 *
 * PATRÓN REPOSITORY:
 * ✅ Abstrae la fuente de datos (API, DB, Mock)
 * ✅ Permite múltiples implementaciones
 * ✅ Facilita testing con mocks
 * ✅ Aplica principio de Inversión de Dependencias (SOLID)
 *
 * IMPLEMENTACIONES:
 * 1. MockGamesRepositoryImpl → Desarrollo local, filtra en memoria
 * 2. GamesRepositoryImpl → Producción, filtra en API/backend
 *
 * BENEFICIOS:
 * - Desacoplamiento: UseCases no saben de dónde vienen los datos
 * - Testing: Fácil crear implementaciones de prueba
 * - Flexibilidad: Cambiar de mock a API sin modificar UseCases
 * - Mantenibilidad: Un solo punto de cambio para origen de datos
 *
 * IMPORTANTE - Resource Pattern:
 * Todos los métodos devuelven Flow<Resource<T>> para manejar:
 * - Loading: Operación en progreso
 * - Success: Datos obtenidos correctamente
 * - Error: Algo falló con información específica
 */
interface GameRepository {

    /**
     * Obtiene todos los juegos disponibles del catálogo
     */
    fun getAllGames(): Flow<Resource<List<Game>>>

    /**
     * Busca los juegos combinando varios criterios de filtrado
     */
    fun getFilteredGames(
        query: String = "",
        category: GameCategory = GameCategory.ALL,
        platform: PlatformEnum = PlatformEnum.ALL,
        interval: DateInterval = DateInterval.ALL_TIME
    ) : Flow<Resource<List<Game>>>

    /**
     * Obtiene un juego por su id
     */
    suspend fun getGameBy(id: Int) : Resource<Game>

    /**
     * Elimina un juego de los favoritos del usuario
     * @param gameId Id del juego a elminar
     */
    suspend fun removeFavorite(gameId: Int)

    /**
     * Añade un juego a favoritos en la cuenta de un usuario
     * @param gameId Id del juego a añadir
     */
    suspend fun addFavorite(gameId: Int)

    /**
     * Idica si un juego esta enta entre los favoritos de un usuario
     * @param gameId Id del juego
     */
    suspend fun isFavorite(gameId: Int) : Boolean

    /**
     * Devuelve un flow
     */
    fun getFavoriteGames(): Flow<Resource<List<Game>>>

    /**
     * Obtiene la nota de un juego si es que está escrita
     * @param gameId Id del juego del que queremos obtener la nota
     * @return Devuelve la entidad nota que posee el juego o null si no hay nada escrito aún
     */
    suspend fun getNote(gameId: Int): GameNote?
}