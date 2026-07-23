package com.pmdm.mygamestore.domain.model

/**
 *  Sealed class que representa el estado de una operación
 *
 * PATRÓN RESOURCE/RESULT:
 * ✅ Manejo explícito de estados (Loading, Success, Error)
 * ✅ Type-safe: El compilador obliga a manejar todos los casos
 * ✅ Errores tipados con información específica
 * ✅ Elimina null checks y excepciones no controladas
 *
 * VENTAJAS:
 * - Exhaustive when: El compilador verifica todos los casos
 * - Sin null: Evita NullPointerException
 * - Errores descriptivos: Sabemos qué falló exactamente
 * - UI reactiva: La UI puede reaccionar a cada estado
 *
 * FLUJO TÍPICO:
 * 1. Loading → Mostrar spinner
 * 2. Success → Mostrar datos
 * 3. Error → Mostrar mensaje de error
 *
 * @param T Tipo de dato que contiene en caso de éxito
 */
sealed class Resource<out T> {

    /**
     * ⏳ Estado: Operación en progreso
     *
     * Se emite al iniciar una operación asíncrona.
     * La UI muestra loading indicator.
     *
     * Ejemplo:
     * ```
     * when (resource) {
     *     is Resource.Loading -> showLoadingSpinner()
     * }
     * ```
     */
    data object Loading : Resource<Nothing>()

    /**
     * ✅ Estado: Operación completada exitosamente
     *
     * Contiene los datos solicitados.
     *
     * @param data Datos obtenidos de la operación
     *
     * Ejemplo:
     * ```
     * when (resource) {
     *     is Resource.Success -> {
     *         val games = resource.data
     *         showGames(games)
     *     }
     * }
     * ```
     */
    data class Success<T>(val data: T) : Resource<T>()

    /**
     * ❌ Estado: Operación falló
     *
     * Contiene información detallada del error.
     *
     * @param error Error específico que ocurrió
     *
     * Ejemplo:
     * ```
     * when (resource) {
     *     is Resource.Error -> {
     *         when (resource.error) {
     *             is AppError.NetworkError -> showNoInternetMessage()
     *             is AppError.NotFound -> showNotFoundMessage()
     *         }
     *     }
     * }
     * ```
     */
    data class Error(val error: AppError) : Resource<Nothing>()

}

/**
 *  Sealed class que representa errores específicos de la app
 *
 * Permite manejar diferentes tipos de errores de forma específica:
 * - Errores de red (sin conexión, timeout)
 * - Errores de base de datos (corrupción, falta de espacio)
 * - Errores de negocio (no encontrado, no autorizado)
 * - Errores de validación
 * - Errores desconocidos
 *
 * VENTAJAS:
 * ✅ Errores tipados y específicos
 * ✅ La UI puede mostrar mensajes personalizados
 * ✅ Fácil logging y analytics
 * ✅ Manejo exhaustivo con when
 */
sealed class AppError {

    /**
     *  Error de red
     *
     * Ocurre cuando:
     * - No hay conexión a Internet
     * - Timeout de la petición
     * - Error del servidor (5xx)
     *
     * @param message Descripción del error
     *
     * Ejemplo de uso:
     * ```
     * when (error) {
     *     is AppError.NetworkError -> {
     *         showSnackbar("Check your internet connection")
     *     }
     * }
     * ```
     */
    data class NetWorkError(val message: String): AppError()

    /**
     *  Error de base de datos
     *
     * Ocurre cuando:
     * - No se puede acceder a la base de datos
     * - Datos corruptos
     * - Falta de espacio en disco
     *
     * @param message Descripción del error
     */
    data class DatabaseError(val message: String): AppError()

    /**
     *  Recurso no encontrado (404)
     *
     * Ocurre cuando:
     * - El juego con ID especificado no existe
     * - La búsqueda no tiene resultados
     * - La categoría no tiene juegos
     *
     * Ejemplo de uso:
     * ```
     * when (error) {
     *     is AppError.NotFound -> {
     *         showEmptyState("No games found")
     *     }
     * }
     * ```
     */
    data object NotFound: AppError()

    /**
     *  No autorizado (401/403)
     *
     * Ocurre cuando:
     * - El usuario no tiene sesión activa
     * - El token de autenticación expiró
     * - No tiene permisos para la operación
     *
     * Ejemplo de uso:
     * ```
     * when (error) {
     *     is AppError.Unauthorized -> {
     *         navigateToLogin()
     *     }
     * }
     * ```
     */
    data object Unauthroized: AppError()

    /**
     * ⚠️ Error de validación
     *
     * Ocurre cuando:
     * - Query de búsqueda inválido
     * - Parámetros fuera de rango
     * - Formato de datos incorrecto
     *
     * @param message Descripción del error de validación
     *
     * Ejemplo de uso:
     * ```
     * when (error) {
     *     is AppError.ValidationError -> {
     *         showError(error.message)
     *     }
     * }
     * ```
     */
    data class ValidationError(val message: String): AppError()

    /**
     * ❓ Error desconocido
     *
     * Ocurre cuando:
     * - Excepción no prevista
     * - Error sin categoría específica
     *
     * @param message Descripción del error
     *
     * Ejemplo de uso:
     * ```
     * when (error) {
     *     is AppError.Unknown -> {
     *         logError(error.message)
     *         showGenericError()
     *     }
     * }
     * ```
     */
    data class Unknown(val message: String): AppError()
}