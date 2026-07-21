package com.pmdm.mygamestore.presentation.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.pmdm.mygamestore.data.local.SessionManagerImpl
import com.pmdm.mygamestore.presentation.ui.navigation.AppNavigation
import com.pmdm.mygamestore.presentation.ui.navigation.AppRoutes
import com.pmdm.mygamestore.presentation.ui.navigation.LocalNavStack
import com.pmdm.mygamestore.presentation.ui.theme.dimens
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val sessionManager = remember { SessionManagerImpl(context) }
    val scope = rememberCoroutineScope()
    val navStack = LocalNavStack.current

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.paddingMedium),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Home Screen",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(MaterialTheme.dimens.medium))

                Text(
                    text = "Welcome to GameVault!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Button(
                onClick = {
                    scope.launch {
                        //Limpiamos sesion del data store
                        sessionManager.clearSession()

                        //Limpiar historial de navegacion
                        navStack.clear()

                        //Navegar a login
                        navStack.add(AppRoutes.Login)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.paddingMedium)
                    .height(MaterialTheme.dimens.buttonHeightMedium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.7f),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Logout DEBUG",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}
