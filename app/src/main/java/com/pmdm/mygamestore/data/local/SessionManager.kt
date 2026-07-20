package com.pmdm.mygamestore.data.local

import kotlinx.coroutines.flow.Flow

interface SessionManager {
    /**
     * Guarda la sesion de un usuario
     * @param username Nombre del usuario autenticado
     */
    suspend fun saveSession(username: String)

    /**
     * Verifica si un usuario tiene la sesion activa o no
     * @return Flow que emite true si la sesion está activa o false en caso contrario
     */
    fun isUserLoggedIn(): Flow<Boolean>

    /**
     * Obtiene el nombre de usuario de la sesion activa
     * @return Flow que emite el username o null si no hay sesion
     */
    fun getUserName(): Flow<String?>

    /**
     * Limpia la sesion del usuario (Logout)
     */
    suspend fun clearSession()

}