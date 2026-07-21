package com.pmdm.mygamestore.presentation.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.mygamestore.viewmodel.RegisterViewModel
import com.pmdm.mygamestore.viewmodel.RegisterViewModelFactory
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.mygamestore.R
import com.pmdm.mygamestore.presentation.ui.components.LabeledTextFieldGS
import com.pmdm.mygamestore.presentation.ui.components.RoundedButton
import com.pmdm.mygamestore.presentation.ui.theme.dimens

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(LocalContext.current)
    ),
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackBarhostState = remember { SnackbarHostState() }

    //Mostramos errores
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            error -> snackBarhostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    //Navegar tras registro exitoso
    LaunchedEffect(uiState.isRegisteredSuccessful) {
        if (uiState.isRegisteredSuccessful) {
            onRegisterSuccess()
            viewModel.resetRegisterSuccess()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarhostState) },
        modifier = Modifier.padding(MaterialTheme.dimens.paddingMedium)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))

                //Username
                LabeledTextFieldGS(
                    label = "Username",
                    value = uiState.username,
                    onValueChange = viewModel::onUsernameChange,
                    placeholder = "Enter your username",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))

                //Password
                LabeledTextFieldGS(
                    label = "Password",
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    placeholder = "Enter your password",
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))

                //Confirm Password
                LabeledTextFieldGS(
                    label = "Confirm Password",
                    value = uiState.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    placeholder = "Confirm your password",
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))

                //Email
                LabeledTextFieldGS(
                    label = "Email",
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    placeholder = "Enter your email",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                //Botton Register
                RoundedButton(
                    texto = if (uiState.isLoading) "Creating Account..." else "Create Account",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeightMedium),
                    onClick = viewModel::onRegisterClick,
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))

                //Link a login
                TextButton(
                    onClick = onNavigateToLogin
                ) {
                    Text(
                        text = "Already have an account? Login",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = MaterialTheme.dimens.paddingMedium)
                )
            }

            //Loading indicator
            if(uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 100.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val viewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory(LocalContext.current))
    RegisterScreen(
        viewModel = viewModel,
        onRegisterSuccess = {},
        onNavigateToLogin = {}
    )
}
