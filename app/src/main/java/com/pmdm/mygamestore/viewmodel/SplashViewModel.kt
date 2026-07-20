package com.pmdm.mygamestore.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.local.SessionManager
import com.pmdm.mygamestore.data.local.SessionManagerImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel para la pantalla Splash
 *
 * Verifica si el usuario tiene sesión activa para decidir la navegación inicial
 *
 * @param context Contexto para crear SessionManager
 * @param sessionManager Manager para verificar la sesión del usuario
 */
class SplashViewModel(
    context: Context,
    private val sessionManager: SessionManager = SessionManagerImpl(context)
): ViewModel() {

    /**
     * Estado que indica si el usuario está logueado
     *
     * Flow convertido a StateFlow para:
     * - Tener un valor inicial (false)
     * - Compartir el mismo Flow entre múltiples observadores
     * - Mantener el último valor emitido
     */
    val isUserLoggedIn: StateFlow<Boolean> = sessionManager.isUserLoggedIn()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
}