package com.pmdm.mygamestore.viewmodel

data class LoginUiState(
    val username: String = "",
    val passworwd: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccesful: Boolean = false
)