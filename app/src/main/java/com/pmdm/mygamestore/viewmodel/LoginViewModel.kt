package com.pmdm.mygamestore.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.local.SessionManager
import com.pmdm.mygamestore.data.local.SessionManagerImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.pmdm.mygamestore.data.repository.AuthRepository
import com.pmdm.mygamestore.data.repository.AuthRepositoryImpl
import com.pmdm.mygamestore.data.repository.LoginResult

class LoginViewModel(
    context: Context,
    private val authRepository: AuthRepository = AuthRepositoryImpl,
    private val sessionManager: SessionManager = SessionManagerImpl(context)
) : ViewModel() {
    //Estado PRIVADO Mutable - Solo el ViewModel puede modificarlo
    private var _uiState = MutableStateFlow(LoginUiState())

    //Estado publico (inmutable) - La UI solo puede leerlo
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el username del estado
     * @param newUserName Nuevo valor del username
     */
    fun onUserNameChange(newUserName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                username = newUserName,
                errorMessage = null //Limpia el error al escribir
            )
        }
    }

    /**
     * Actualiza el password del estado
     * @param newPassword Nuevo valor del password
     */
    fun onPasswordChange(newPassword: String){
        _uiState.update { currentState ->
            currentState.copy(
                passworwd = newPassword,
                errorMessage = null //Limpia el error al escribir
            )
        }
    }

    /**
     * Ejecuta el proceso de login
     */
    fun onLoginClick(){
        if(_uiState.value.username.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Username cannot be empty") }
            return
        }

        if (_uiState.value.passworwd.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Password cannot be empty") }
            return
        }

        //Proceso de login
        viewModelScope.launch {
            //Mostramos Loading
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            val result = authRepository.validateCredentials(
                username = _uiState.value.username,
                password = _uiState.value.passworwd
            )

           //Llamamos al respository
            when(result) {
                is LoginResult.Success -> {
                    sessionManager.saveSession(result.username)

                    _uiState.update { it.copy(
                        isLoading = false,
                        isLoginSuccesful = true,
                        errorMessage = null
                    ) }
                }

                is LoginResult.Error -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        isLoginSuccesful = false,
                        errorMessage = result.message
                    ) }
                }
            }
        }
    }

    /**
     * Resetea el mensaje de error de un estado
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * Resete el estado de login exitoso
     */
    fun resetLoginSucces() {
        _uiState.update { it.copy(isLoginSuccesful = false) }
    }
}