package com.pmdm.mygamestore.data.repository

interface AuthRepository {
    suspend fun validateCredentials(username: String, password: String) : LoginResult
}