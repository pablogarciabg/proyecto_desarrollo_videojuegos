package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.domain.model.Platform

@Composable
fun PlatformInconsRow(
    platforms: List<Platform>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Recorremos las plataformas asociadas al juego
        platforms.forEach { platform ->
            val icon = getPlatformIcon(platform.slug ?: platform.name)

            Icon(
                imageVector = icon,
                contentDescription = platform.name,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp) // Tamaño discreto ideal para la tarjeta
            )
        }
    }
}

/**
 * Función auxiliar para mapear el slug/nombre de la plataforma
 * con su icono de Material Icons equivalente.
 */
private fun getPlatformIcon(platformIdentifier: String): ImageVector {
    val name = platformIdentifier.lowercase()

    return when {
        name.contains("pc") || name.contains("windows") || name.contains("mac") || name.contains("linux") -> {
            Icons.Default.Computer
        }
        name.contains("playstation") || name.contains("ps") -> {
            Icons.Default.SportsEsports
        }
        name.contains("xbox") -> {
            Icons.Default.Gamepad
        }
        name.contains("nintendo") || name.contains("switch") -> {
            Icons.Default.Tv
        }
        name.contains("android") || name.contains("ios") || name.contains("mobile") -> {
            Icons.Default.PhoneAndroid
        }
        else -> Icons.Default.SportsEsports // Icono por defecto si no coincide
    }
}