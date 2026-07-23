package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes

/**
 * Modelo de datos interno para definir cada ítem del menú de navegación.
 */
private data class NavigationItem(
    val route: AppRoutes,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BottomNavigationBar(
    currentRoute: AppRoutes,
    onNavigate: (AppRoutes) -> Unit,
    modifier: Modifier = Modifier
) {
    // Definimos las pestañas de navegación principal de la app
    val items = listOf(
        NavigationItem(
            route = AppRoutes.Home,
            label = "Inicio",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        /*
        NavigationItem(
            route = AppRoutes.Favorites, // Ajusta la ruta según tus AppRoutes
            label = "Favoritos",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder
        ),
        */
        NavigationItem(
            route = AppRoutes.Profile, // Ajusta la ruta según tus AppRoutes
            label = "Perfil",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )

    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavigate(item.route)
                    }
                },
                label = { Text(text = item.label) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                }
            )
        }
    }
}