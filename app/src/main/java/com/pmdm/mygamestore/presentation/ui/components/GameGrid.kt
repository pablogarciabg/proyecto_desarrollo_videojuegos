package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.domain.model.Game // Importa tu modelo de Game

/**
 * Muestra una cuadrícula adaptable de juegos.
 *
 * @param games Lista de juegos a mostrar.
 * @param onGameClick Callback que devuelve el ID del juego pulsado.
 */
@Composable
fun GameGrid(
    games: List<Game>,
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        // GridCells.Adaptive ajusta automáticamente el número de columnas
        // según el ancho de la pantalla (mínimo 160dp por tarjeta)
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = games,
            key = { game -> game.id } // Usar la key ayuda a la optimización del scroll
        ) { game ->
            GameCard(
                game = game,
                modifier = Modifier.clickable { onGameClick(game.id) },
                onClick = { onGameClick(game.id) }
            )
        }
    }
}