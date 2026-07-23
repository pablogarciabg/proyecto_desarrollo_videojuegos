package com.pmdm.mygamestore.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.domain.GameUseCases
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val gameResource: Resource<Game> = Resource.Loading,
    val isFavorite: Boolean = true
)

class DetailViewModel(
    private val useCases: GameUseCases,
    private val gameId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadGame() //Caragmos el juego al iniciar
    }

    fun loadGame() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(gameResource = Resource.Loading)
            }
            val result = useCases.getGameBy(gameId)
            _uiState.update { it.copy(gameResource = result) }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentlyFavorite = _uiState.value.isFavorite

            // Actualización optimista: cambiamos la UI antes de esperar la respuesta
            _uiState.update { it.copy(isFavorite = !currentlyFavorite) }

            try {
                if(currentlyFavorite) {
                    useCases.removeFavorite(gameId)
                } else {
                    useCases.addFavorite(gameId)
                }
            } catch (e: Exception) {
                //Si falla, revertimos el cambio optimista
                _uiState.update { it.copy(isFavorite = currentlyFavorite) }
            }
        }
    }
}