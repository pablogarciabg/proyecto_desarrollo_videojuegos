package com.pmdm.mygamestore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pmdm.mygamestore.domain.GameUseCases

class DetailViewModelFactory(
    private val useCases: GameUseCases,
    private val gameId: Int
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(useCases, gameId) as T
    }
}