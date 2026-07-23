package com.pmdm.mygamestore.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClose: () -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    val keyBoardController = LocalSoftwareKeyboardController.current

    //Forzar que el teclao se habra automaticamente al pasar al modo de busqueda
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        modifier = modifier,
        title = {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(
                        text = "Search games...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                // Aplicamos el focusquester la text field
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                //Configuramois la tecla del teclado como buscar
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyBoardController?.hide() //Oculta el teclado al pulsar en buscar
                    }
                ),
                // Limpiamos los colores de fondo del TextField para que quede transparente e integrado
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, // Sin línea inferior
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        },
        //Boton de atras
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Cerrar Búsqueda"
                )
            }
        },
        //Botones de acción
        actions = {
            //Si hay texto escrito mostramos el boton de borrar (x)
            if(query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar"
                    )
                }
            } else {
                //Si esta vacío mostramos la lupa como icono decorativo
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}