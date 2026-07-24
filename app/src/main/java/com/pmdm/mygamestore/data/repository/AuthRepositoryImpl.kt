package com.pmdm.mygamestore.data.repository

import kotlinx.coroutines.delay

object AuthRepositoryImpl : AuthRepository {

    //Usuarios validos para login
    val users = mutableMapOf(
        "pablo" to UserData("pablo", "pablogarciabg@gmail.com", "1234"),
        "usuario" to UserData("admin", "admin@gmail.com", "1234")
    )

    //Usuarios registrados en nuestra base de datos local
    private val registeredUsers = mutableMapOf<String, UserData>(
        "pablo" to UserData("pablo", "pablogarciabg@gmail.com", "1234"),
        "admin" to UserData("admin", "admin@gmail.com", "1234")
    )

    override suspend fun validateCredentials(
        username: String,
        password: String
    ) : LoginResult {
        delay(1500) //Simula la llamada a una API remota

        val user = registeredUsers[username]

        return if (user != null && user.password == password) {
            LoginResult.Success(username = user.username)
        } else {
            LoginResult.Error(message = "Invalid username or password")
        }
    }

    override suspend fun register(username: String, email: String, password: String): RegisterResult {
        delay(1500)

        //Verificamos si ya existe el usuario
        if(registeredUsers.containsKey(username)) {
            return RegisterResult.Error("El usuario ya existe")
        }

        //Verificamos si el email ya esta registrado
        if(registeredUsers.values.any{ it.email == email }) {
            return RegisterResult.Error("El email ya está registrado")
        }

        //Registramos nuevo usuario
        registeredUsers[username] = UserData(username, email, password)
        users[username] = UserData(username, email, password)

        return RegisterResult.Success(username)
    }
}

/**
 * Clase de datos para almacenar la información de cada usuario
 */
data class UserData(
    val username: String,
    val email: String,
    val password: String
)

