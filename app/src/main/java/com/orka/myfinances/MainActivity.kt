package com.orka.myfinances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.application.MyFinancesApplication
import com.orka.myfinances.application.printer.getPermissions
import com.orka.myfinances.ui.screens.host.HostScreen
import com.orka.myfinances.ui.theme.MyFinancesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()
        getPermissions()

        setContent {
            val app = application as MyFinancesApplication
            val manager = viewModel { app.manager() }
            val uiState = manager.uiState.collectAsState()

            MyFinancesTheme {
                HostScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = uiState.value,
                    guestRuntimeInitializer = app.guestRuntimeInitializer,
                    newUserRuntimeInitializer = app.newUserRuntimeInitializer,
                    signedInRuntimeInitializer = app.signedInRuntimeInitializer,
                    interactor = manager
                )
            }
        }
    }
}