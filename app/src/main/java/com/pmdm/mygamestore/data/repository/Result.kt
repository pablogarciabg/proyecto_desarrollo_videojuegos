package com.pmdm.mygamestore.data.repository

sealed class LoginResult {
    /**
     * Login exitoso
     * @param username Nombre del usuario autenticado
     */
    data class Success(val username: String) : LoginResult()

    /**
     * Login fallido
     * @param message Mensaje describiendo el error
     */
    data class Error(val message: String) : LoginResult()
}