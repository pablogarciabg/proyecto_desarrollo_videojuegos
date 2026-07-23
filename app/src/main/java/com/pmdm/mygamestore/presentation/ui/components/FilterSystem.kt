package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.domain.model.DateInterval
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.PlatformEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSystem(
    selectedCategory: GameCategory,
    onCategorySelected: (GameCategory) -> Unit,
    selectedPlatform: PlatformEnum,
    onPlatformSelected: (PlatformEnum) -> Unit,
    selectedInterval: DateInterval,
    onIntervalSelected: (DateInterval) -> Unit,
    onClearFilters: () -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            CompactFilterChip(
                label = "Categoría",
                isSelected = selectedCategory != GameCategory.ALL,
                onClick = { showSheet = true }
            )
        }
        // Aquí podrás agregar los chips para Platform, Interval y el botón de Limpiar
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Selecciona una categoría", style = MaterialTheme.typography.titleMedium)
                // Tu lista de opciones...
            }
        }
    }
}