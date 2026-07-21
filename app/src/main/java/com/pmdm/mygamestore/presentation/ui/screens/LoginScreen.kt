package com.pmdm.mygamestore.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.mygamestore.R
import com.pmdm.mygamestore.presentation.ui.theme.MyGameStoreTheme
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import com.pmdm.mygamestore.viewmodel.LoginViewModel
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalContext
import com.pmdm.mygamestore.presentation.ui.components.LabeledTextFieldGS
import com.pmdm.mygamestore.presentation.ui.components.RoundedButton
import com.pmdm.mygamestore.viewmodel.LoginViewModelFactory

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(LocalContext.current)
    ),
    onLoginSuccess: () -> Unit = {},  //Callback de navegacion
    onNavigateToRegister: () -> Unit = {}
) {
    var username by remember() { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //Observamos estado y nos nitifica cuando cambia
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    //Detectamos cuando el login es exitoso
    LaunchedEffect(uiState.isLoginSuccesful) {
        if(uiState.isLoginSuccesful) {
            onLoginSuccess() //Ejecutamos er callback
            viewModel.resetLoginSucces()
        }
    }

    //Mostramos errores
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    //Navegar cuando el login es exitoso
    LaunchedEffect(uiState.isLoginSuccesful) {
        if(uiState.isLoginSuccesful) {
            onLoginSuccess()
            viewModel.resetLoginSucces()
        }
    }

    Scaffold(
        topBar = {},
        modifier = Modifier.padding(MaterialTheme.dimens.paddingMedium),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                LabeledTextFieldGS(
                    label = "Username",
                    value = uiState.username,
                    onValueChange = { newValue -> viewModel.onUserNameChange(newValue) },
                    placeholder = "Enter your username",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))

                LabeledTextFieldGS(
                    label = "Password",
                    value = uiState.passworwd,
                    onValueChange = { newValue -> viewModel.onPasswordChange(newValue) },
                    placeholder = "Enter your password",
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                //Boton de login
                RoundedButton(
                    texto = if(uiState.isLoading) "Loading..." else "Login",
                    colorFondo = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeightMedium),
                    onClick = {
                        println("BOTON PULSADO")
                        viewModel.onLoginClick()
                    },
                    enabled = !uiState.isLoading
                )

                //Loading Indicator
                if(uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = MaterialTheme.dimens.medium)
                    )
                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                Text(
                    text = "Register",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.weight(1f))

                //Texto al final de la pagina
                TextButton(
                    onClick = onNavigateToRegister

                ) {
                    Text(
                        text = "Don't have an account? Register",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MyGameStoreTheme {
        LoginScreen()
    }
}
