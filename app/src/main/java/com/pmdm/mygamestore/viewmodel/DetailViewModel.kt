package com.pmdm.mygamestore.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import com.pmdm.mygamestore.domain.GameUseCases
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameNote
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val gameResource: Resource<Game> = Resource.Loading,
    val isFavorite: Boolean = true,
    val note: GameNote? = null
)

class DetailViewModel(
    private val useCases: GameUseCases,
    private val gameId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadGame() //Caragmos el juego al iniciar
        checkIfFavorite()
        loadNote()
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

    fun checkIfFavorite() {
        viewModelScope.launch {
            val isFav = useCases.isFavorite(gameId)
            _uiState.update {
                it.copy(isFavorite = isFav)
            }
        }
    }

    fun loadNote() {
        viewModelScope.launch {
            val note = useCases.getNote(gameId)
            _uiState.update { it.copy(note = note) }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            //Actualiza Room y la UI reaccionará sola gracias al FLow
            useCases.toggleFavorite(gameId)

        }
    }
}