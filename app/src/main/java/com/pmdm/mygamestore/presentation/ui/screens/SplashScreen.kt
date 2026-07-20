package com.pmdm.mygamestore.presentation.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.mygamestore.R
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import com.pmdm.mygamestore.viewmodel.LoginViewModel
import com.pmdm.mygamestore.viewmodel.SplashViewModel
import com.pmdm.mygamestore.viewmodel.SplashViewModelFactory
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel(
        factory = SplashViewModelFactory(LocalContext.current)
    ),
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val navStack = LocalNavStack.current

    //Observar el estado de la sesion
    val isLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    //Verificar sesion despues del delay
    LaunchedEffect(Unit) {
        delay(2000) //Mostramos logo durante 2 segs

        ///Decidimos navegacion segun estado de la sesion
        if(isLoggedIn) onNavigateToHome() else onNavigateToLogin()
    }

    //UI de la Splash Screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo de la app",
            modifier = Modifier.size(200.dp)
        )
    }
}
