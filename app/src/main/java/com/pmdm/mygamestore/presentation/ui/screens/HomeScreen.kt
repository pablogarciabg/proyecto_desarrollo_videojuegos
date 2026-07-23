package com.pmdm.mygamestore.presentation.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.loadingindicator.LoadingIndicator
import com.pmdm.mygamestore.data.local.SessionManagerImpl
import com.pmdm.mygamestore.presentation.ui.components.BottomNavigationBar
import com.pmdm.mygamestore.presentation.ui.components.EmptyState
import com.pmdm.mygamestore.presentation.ui.components.ErrorMessage
import com.pmdm.mygamestore.presentation.ui.components.FilterSystem
import com.pmdm.mygamestore.presentation.ui.components.GameGrid
import com.pmdm.mygamestore.presentation.ui.components.MainTopBar
import com.pmdm.mygamestore.presentation.ui.components.SearchTopBar
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import com.pmdm.mygamestore.viewmodel.HomeViewModel
import com.pmdm.mygamestore.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(context = context)
    )
) {
    val sessionManager = remember { SessionManagerImpl(context) }
    val scope = rememberCoroutineScope()
    val navStack = LocalNavStack.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            if (uiState.isSearchMode) {
                // Barra con campo de texto de búsqueda
                SearchTopBar(
                    query = uiState.searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChange(it) },
                    onClose = { viewModel.toggleSeachMode() },
                    focusRequester = focusRequester
                )
            } else {
                // Barra estándar con título y botones de acción
                MainTopBar(
                    onToggleFilters = { viewModel.toggleFilterVisibility() },
                    onOpenSearch = { viewModel.toggleSeachMode() }
                )
            }
        },
        bottomBar = {
            // Navegación principal de la app
            BottomNavigationBar(
                currentRoute = AppRoutes.Home,
                onNavigate = { route ->
                    navStack.clear()
                    navStack.add(route)
                }
            )
        }
    ) { innerPadding ->
        // Manteniendo tu Box principal con el innerPadding
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Columna interna que distribuye los filtros y el contenido
            Column(modifier = Modifier.fillMaxSize()) {

                // 1. FILTROS CON ANIMACIÓN
                AnimatedVisibility(
                    visible = uiState.isFilterVisible,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    FilterSystem(
                        selectedCategory = uiState.selectedCategory,
                        onCategorySelected = { category -> viewModel.onCategorySelected(category) },
                        selectedPlatform = uiState.selectedPlatform,
                        onPlatformSelected = { platform -> viewModel.onPlatformSelected(platform) },
                        selectedInterval = uiState.selectedInterval,
                        onIntervalSelected = { interval -> viewModel.onIntervalSelected(interval) },
                        onClearFilters = { viewModel.clearAllFilters() }
                    )
                }

                // 2. CONTENIDO PRINCIPAL (Ocupa el espacio restante)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when {
                        //Estado 1: Cargando los datos
                        uiState.isLoading -> {
                            CircularProgressIndicator()
                        }

                        //Estado 2: Ocurrio un error
                        uiState.errorMessage != null ->
                            ErrorMessage(
                                message = uiState.errorMessage!!,
                                onRetry = { viewModel.refreshGames() }
                            )

                        //Estado 3: La lista de juegos esta vacía
                        uiState.games.isEmpty() -> EmptyState( onClearFilters = { viewModel.clearAllFilters() })

                        //Estado 4: Éxito
                        else -> GameGrid(games = uiState.games, onGameClick = { /* Navegar al detalle */ })
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                // Limpiamos sesión del data store
                                sessionManager.clearSession()

                                // Limpiar historial de navegación
                                navStack.clear()

                                // Navegar a login
                                navStack.add(AppRoutes.Login)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(MaterialTheme.dimens.paddingMedium)
                            .height(MaterialTheme.dimens.buttonHeightMedium),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.7f),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Logout DEBUG",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}