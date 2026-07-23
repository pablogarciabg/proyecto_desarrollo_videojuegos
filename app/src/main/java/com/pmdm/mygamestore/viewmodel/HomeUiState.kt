package com.pmdm.mygamestore.viewmodel

import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.PlatformEnum

data class HomeUiState(
    val games: List<Game> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val username: String? = null,

    //Busqueda y Filtros
    val isSearchMode: Boolean = false,
    val isFilterVisible: Boolean = false,
    val searchQuery: String = "",
    val selectedCategory: GameCategory = GameCategory.ALL,
    val selectedPlatform: PlatformEnum = PlatformEnum.ALL,
    val selectedInterval: DateInterval = DateInterval.ALL_TIME
)