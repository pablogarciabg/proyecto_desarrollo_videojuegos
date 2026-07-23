package com.pmdm.mygamestore.presentation.ui.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.mygamestore.data.repository.GameRepository
import com.pmdm.mygamestore.data.repository.MockGamesRepositoryImpl
import com.pmdm.mygamestore.domain.GameUseCases
import com.pmdm.mygamestore.domain.model.Resource
import com.pmdm.mygamestore.presentation.ui.components.ErrorMessage
import com.pmdm.mygamestore.presentation.ui.components.ExpandableText
import com.pmdm.mygamestore.presentation.ui.components.ImageCarousel
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import com.pmdm.mygamestore.viewmodel.DetailViewModel
import com.pmdm.mygamestore.viewmodel.DetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    gameId: Int,
    onBack: () -> Unit
) {
    android.util.Log.d("DetailScreen", "Componiendo DetailScreen para gameId=$gameId")
    val context = LocalContext.current

    val repository: GameRepository = remember(context) {
        MockGamesRepositoryImpl()
    }
    val viewModel: DetailViewModel = viewModel(
        // 🔑 Esta clave fuerza a crear un nuevo VM si el ID cambia
        key = "DetailViewModel_$gameId",
        factory = DetailViewModelFactory(GameUseCases(repository), gameId)
    )

    val uiState by viewModel.uiState.collectAsState()

    // ... Resto del Scaffold con LazyColumn para el contenido
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                }
            )
        }
    ) {
        paddingValue ->
        when(val resource = uiState.gameResource) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Error -> {
                ErrorMessage(message = "Something went wrong", onRetry = onBack)
            }

            is Resource.Success -> {
                val game = resource.data

                // ⭐ Aquí es donde nace "images": no viene del modelo directamente,
                // se construye combinando portada + capturas de pantalla
                val images = remember(game) {
                    listOf(game.imageUrl) + game.screenshots.map { it.imageUrl }
                }

                LazyColumn(modifier = Modifier.padding(paddingValue)) {
                    item {
                        ImageCarousel(images)
                    }
                    item {
                        Column(modifier = Modifier.padding(MaterialTheme.dimens.paddingMedium)) {
                            Text(
                                text = game.title,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp
                                )
                            )

                            //Boton de favoritos junto al titulo
                            IconButton(
                                onClick = { viewModel.toggleFavorite() },
                                modifier = Modifier.size(MaterialTheme.dimens.buttonHeightLarge)
                            ) {
                                Icon(
                                    imageVector = if (uiState.isFavorite) {
                                        Icons.Default.Favorite
                                    } else {
                                        Icons.Default.FavoriteBorder
                                    },
                                    contentDescription = "Favorito",
                                    tint = if(uiState.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            // ... Rating, Developers, Genres (InfoColumn, MetacriticBadge, TagsFlowRow) ...
                            ExpandableText(game.description)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(gameId = 12, onBack = {})
}
