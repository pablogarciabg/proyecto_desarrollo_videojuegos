package com.pmdm.mygamestore.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *  Factory para crear HomeViewModel
 *
 * PROPÓSITO:
 * - ViewModel necesita Context para SessionManager
 * - ViewModelProvider.Factory permite pasar parámetros al constructor
 *
 * @param context Contexto de Android
 */
class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(context) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class: ${modelClass.name}")
    }
}