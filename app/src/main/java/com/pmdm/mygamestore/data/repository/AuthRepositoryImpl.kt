package com.pmdm.mygamestore.data.repository

import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {

    val users = mapOf(
        "pablo" to "1234",
        "usuario" to "contraseña"
    )

    override suspend fun validateCredentials(
        username: String,
        password: String
    ) : LoginResult {
        delay(1500) //Simula la llamada a una API remota

        return if(users[username] == password) {
            LoginResult.Success(username = username)
        } else {
            LoginResult.Error(message = "Invalid username or password")
        }
    }
}