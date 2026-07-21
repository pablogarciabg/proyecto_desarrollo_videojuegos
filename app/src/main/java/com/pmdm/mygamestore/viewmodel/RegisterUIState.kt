package com.pmdm.mygamestore.viewmodel

data class RegisterUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegisteredSuccessful: Boolean = true
)