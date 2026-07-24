package com.pmdm.mygamestore.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.mygamestore.data.local.SessionManager
import com.pmdm.mygamestore.data.local.SessionManagerImpl
import com.pmdm.mygamestore.data.repository.GameRepository
import com.pmdm.mygamestore.data.repository.MockGamesRepositoryImpl
import com.pmdm.mygamestore.domain.GameUseCases
import com.pmdm.mygamestore.domain.model.AppError
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar el estado de la pantalla principal, específicamente para cargar,
 * filtrar y gestionar una lista de juegos, así como también administrar elementos relacionados
 * con la sesión del usuario.
 *
 * Este ViewModel encapsula la lógica de negocio necesaria para interactuar con los datos de juegos,
 * incluidos filtros, modos de búsqueda y control de errores. Además, interactúa con un gestor de sesión
 * para manejar datos del usuario como el nombre de usuario.
 *
 * Principales responsabilidades:
 * - Gestionar el estado inmutable y mutable de la vista utilizando `StateFlow`.
 * - Cargar los juegos aplicando diferentes tipos de filtros, búsqueda y criterios temporales.
 * - Soportar funcionalidades de gestión, incluyendo modos de búsqueda, visibilidad de filtros y limpieza de errores.
 * - Administrar estados de carga y manejo de errores, proporcionando mensajes de error descriptivos basados en diferentes fallos.
 *
 * Dependencias internas utilizadas:
 * - `GamesRepository`: Para obtener la lista de juegos y aplicar filtros sobre estos.
 * - `GameUseCases`: Un conjunto de casos de uso relacionados con juegos.
 * - `SessionManager`: Para gestionar información de la sesión de usuario, como el nombre de usuario.
 *
 * Métodos principales:
 * - `loadGames`: Carga y filtra juegos según los criterios actuales.
 * - `onSearchQueryChange`: Actualiza la query de búsqueda y recarga los juegos.
 * - `toggleSearchMode`: Activa o desactiva el modo de búsqueda y ajusta otros estados con base en esta acción.
 * - `toggleFilterVisibility`: Muestra u oculta el panel de filtros.
 * - `onCategorySelected`, `onPlatformSelected`, `onIntervalSelected`: Aplica filtros específicos y recarga los juegos.
 * - `refreshGames`: Recarga la lista de juegos, útil para acciones como pull-to-refresh.
 * - `clearError`: Limpia cualquier mensaje de error activo.
 * - `clearAllFilters`: Reinicia todos los filtros a sus valores por defecto y recarga los juegos.
 *
 * Esta clase promueve la separación de responsabilidades y coordina entre la capa de datos
 * (repositorios y casos de uso) y la capa de presentación (UI).
 */
class HomeViewModel(
    context: Context
) : ViewModel() {

    //Dependecias instanciadas directamente
    private val gamesRepository: GameRepository = MockGamesRepositoryImpl(context)
    private val gameUseCases = GameUseCases(gamesRepository)
    private val sessionManager: SessionManager = SessionManagerImpl(context)

    //Estado privado mutable
    private val _uiState = MutableStateFlow(HomeUiState())

    //Estado público inmutable
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUsername()
        loadGames()
    }

    private fun loadUsername() {
        viewModelScope.launch {
            sessionManager.getUserName()
                .catch { exception ->
                    println("Error loading the username: ${exception.message}")
                }
                .collect { username ->
                    _uiState.update { it.copy(username = username) }
                }
        }
    }

    fun loadGames() {
        viewModelScope.launch {
            val currentState = _uiState.value

            val gamesFlow = gameUseCases.getFilteredGames(
                query = currentState.searchQuery,
                category = currentState.selectedCategory,
                platform = currentState.selectedPlatform,
                interval = currentState.selectedInterval
            )

            gamesFlow.collect { resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                games = resource.data,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        val errorMsg = when(resource.error) {
                            is AppError.NetWorkError ->
                                "No internet connection. Please check your network."
                            is AppError.NotFound ->
                                "No games found."
                            is AppError.DatabaseError ->
                                "Database error. Please try again."
                            is AppError.Unauthroized ->
                                "You need to login to access this content."
                            is AppError.ValidationError ->
                                resource.error.message
                            is AppError.Unknown ->
                                resource.error.message
                        }

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = errorMsg
                            )
                        }
                    }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String){
        _uiState.update { it.copy(searchQuery = query) }
        loadGames()
    }

    fun toggleSeachMode() {
        _uiState.update {
            val newSearchMode = !it.isSearchMode
            it.copy(
                isSearchMode = newSearchMode,
                //Si abrimos búsqueda, cerramos filtros
                isFilterVisible = if (newSearchMode) false else it.isFilterVisible,
                //Si cerramos la búsqueda, limiamos la query
                searchQuery = if (!newSearchMode) "" else it.searchQuery
            )
        }
        //Si acabamos de limpiar la búsqueda al cerrar el modo, recgargamos juegos
        if(!_uiState.value.isSearchMode) {
            loadGames()
        }
    }

    fun onCategorySelected(category: GameCategory) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
        loadGames()
    }

    fun onPlatformSelected(platform: PlatformEnum) {
        _uiState.update {
            it.copy(selectedPlatform = platform)
        }
        loadGames()
    }

    fun onIntervalSelected(interval: DateInterval) {
        _uiState.update {
            it.copy(selectedInterval = interval)
        }
        loadGames()
    }

    fun refreshGames() {
        loadGames()
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun clearAllFilters() {
        _uiState.update {
            it.copy(
                searchQuery = "",
                selectedCategory = GameCategory.ALL,
                selectedPlatform = PlatformEnum.ALL,
                selectedInterval = DateInterval.ALL_TIME
            )
        }
        loadGames()
    }

    fun toggleFilterVisibility() {
        _uiState.update { currentState ->
            val newFilterVisibility = !currentState.isFilterVisible
            currentState.copy(
                isFilterVisible = newFilterVisibility,
                // Opcional: Si abrimos los filtros, podemos cerrar la búsqueda activa para no recargar la pantalla
                isSearchMode = if (newFilterVisibility) false else currentState.isSearchMode
            )
        }
    }
}