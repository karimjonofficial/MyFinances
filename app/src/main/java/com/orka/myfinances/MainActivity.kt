package com.orka.myfinances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.fixtures.SessionDataSourceImpl
import com.orka.myfinances.lib.AndroidLogger
import com.orka.myfinances.ui.MyFinancesContent
import com.orka.myfinances.ui.theme.MyFinancesTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val logger = AndroidLogger()
            val sessionDataSource = SessionDataSourceImpl()
            val manager = UiManager(logger, sessionDataSource, Dispatchers.Default)
            val uiState = manager.uiState.collectAsState()
            manager.initialize()

            MyFinancesTheme {
                MyFinancesContent(
                    modifier = Modifier.fillMaxSize(),
                    state = uiState.value
                )
            }
        }
    }
}

