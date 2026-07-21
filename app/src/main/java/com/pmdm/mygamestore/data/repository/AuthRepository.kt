package com.pmdm.mygamestore.data.repository

interface AuthRepository {
    suspend fun validateCredentials(username: String, password: String) : LoginResult //Se corresponde con login en los apuntes

    /**
     * Registra un nuevo usuario
     *
     * @param username Nombre de usuario
     * @param email Email del usuario
     * @param password Contraseña
     * @return RegisterResult indicando éxito o error
     */
    suspend fun register(
        username: String,
        email: String,
        password: String
    ) : RegisterResult
}