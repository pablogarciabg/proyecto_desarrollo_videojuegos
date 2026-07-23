package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.domain.model.Game

@Composable
fun GameCard(
    game: Game,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
            .clickable {
                android.util.Log.d("GameCard", "Click detectado en GameCard, id=${game.id}")
                onClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Carrusel de imágenes (Uso de HorizontalPager)
            // ... (Ver implementación completa en el repositorio)

            //Informacion del juego
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Iconos de Plataforma (Windows, PS, Xbox...)
                    PlatformInconsRow(platforms = game.platforms)
                    // Rating con estrella
                    // ...
                }
                // Título (Limitado a 1 línea para mantener simetría)
                Text(text = game.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}