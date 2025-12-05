package com.orka.myfinances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.ui.screens.MyFinancesScreen
import com.orka.myfinances.ui.theme.MyFinancesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val manager = (application as MyFinancesApplication).manager
            val uiState = manager.uiState.collectAsState()
            manager.initialize()

            MyFinancesTheme {

                MyFinancesScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = uiState.value
                )
            }
        }
    }
}

sealed interface Dest {
    data class Dest1(val id: Int) : Dest
    data class Dest2(val id: Int) : Dest
}

@Preview
@Composable
private fun PredictivePreview() {
    val backStack = rememberSaveable { mutableStateListOf<Dest>(Dest.Dest1(1)) }

    MyFinancesTheme {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Dest.Dest1> { key ->
                    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                        Column(
                            modifier = Modifier.scaffoldPadding(paddingValues).background(Color.Green),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = key.id.toString())
                            Button(onClick = { backStack.add(Dest.Dest2(key.id + 1)) }) {
                                Text(text = "Navigate to ${key.id + 1}")
                            }
                        }
                    }
                }

                entry<Dest.Dest2> { key ->
                    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                        Column(
                            modifier = Modifier.scaffoldPadding(paddingValues).background(Color.Green),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = key.id.toString())
                            Button(onClick = { backStack.add(Dest.Dest1(key.id + 1)) }) {
                                Text(text = "Navigate to ${key.id + 1}")
                            }
                        }
                    }
                }
            }
        )
    }
}