package com.pmdm.mygamestore.data.repository

sealed class RegisterResult {
    /**
     * Registro exitoso
     * @param username Nombre de usuario registrado
     */
    data class Success(val username: String) : RegisterResult()

    /**
     * Fallo en el registro
     * @param message Mensaje de error del registro
     */
    data class Error(val message: String) : RegisterResult()
}