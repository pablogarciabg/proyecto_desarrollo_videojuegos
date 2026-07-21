package com.pmdm.mygamestore.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.local.SessionManager
import com.pmdm.mygamestore.data.local.SessionManagerImpl
import com.pmdm.mygamestore.data.repository.AuthRepository
import com.pmdm.mygamestore.data.repository.AuthRepositoryImpl
import com.pmdm.mygamestore.data.repository.RegisterResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar el estado y la lógica de la pantalla de Registro
 */
class RegisterViewModel(
    context: Context,
    private val authRepository: AuthRepository = AuthRepositoryImpl,
    private val sessionManager: SessionManager = SessionManagerImpl(context)
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState = _uiState

    fun onUsernameChange(newUsername: String) {
        _uiState.update {
            it.copy(username = newUsername, errorMessage = null)
        }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword, errorMessage = null)
        }
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _uiState.update {
            it.copy(confirmPassword = newPassword, errorMessage = null)
        }
    }

    fun onEmailChange(newEmail: String){
        _uiState.update {
            it.copy(
                email = newEmail,
                errorMessage = null
            )
        }
    }

    /**
     * Ejecuta el proceso de registro con validaciones
     */
    fun onRegisterClick() {
        val state = _uiState.value

        //Validacion:Username
        if (state.username.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Username cannot be empty")
                return
            }
        }

        //Validacion de contraseña
        if(state.password.isBlank()) {
            _uiState.update {
                it.copy("Password cannot be empty")
            }
        }

        //Validacion de contraseña de confirmacion
        if(state.confirmPassword.isBlank()) {
            _uiState.update {
                it.copy("Confirm password cannot be empty")
            }
        }

        //Validacion de password == confirmPassword
        if(state.password != state.confirmPassword) {
            _uiState.update {
                it.copy("Password missmatch the Confirm Password")
            }
        }

        //Proceso de registro
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            val result = authRepository.register(
                username = state.username,
                email = state.email,
                password = state.password
            )

            when(result) {
                is RegisterResult.Success -> {
                    //Guardamos sesion tras registro
                    sessionManager.saveSession(result.username)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRegisteredSuccessful = true
                        )
                    }
                }

                is RegisterResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

    fun resetRegisterSuccess() {
        _uiState.update {
            it.copy(isRegisteredSuccessful = false)
        }
    }
}