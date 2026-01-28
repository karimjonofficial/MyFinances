package com.orka.myfinances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.application.MyFinancesApplication
import com.orka.myfinances.ui.screens.main.MainScreen
import com.orka.myfinances.ui.theme.MyFinancesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val manager = (application as MyFinancesApplication).manager()
            val uiState = manager.uiState.collectAsState()

            MyFinancesTheme {
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = uiState.value
                )
            }
        }
    }
}