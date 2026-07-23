package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    onToggleFilters: () -> Unit,
    onOpenSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "GameVault",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        actions = {
            // Botón para abrir el modo de búsqueda
            IconButton(onClick = onOpenSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar juegos"
                )
            }
            // Botón para alternar la visibilidad de los filtros
            IconButton(onClick = onToggleFilters) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Mostrar o ocultar filtros"
                )
            }
        }
    )
}