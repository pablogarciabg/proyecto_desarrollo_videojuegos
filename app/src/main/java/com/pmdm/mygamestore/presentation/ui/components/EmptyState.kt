package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyState(
    message: String = "",
    onClearFilters: (() -> Unit)? = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Default.SearchOff, modifier = Modifier.size(64.dp), contentDescription = "Icono de Búsqueda")
        Text(text = message)
        onClearFilters?.let {
            RoundedButton(texto = "Clear Filters", onClick = it)
        }
    }
}