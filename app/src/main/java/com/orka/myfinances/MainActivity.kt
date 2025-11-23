package com.orka.myfinances

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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

/**@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun TextFieldTransformations() {
//                onValueChange = {
//                    val doubleOrNull = it.parseFromFormatted()
//                    double.value = doubleOrNull ?: 0.0
//                    text.value = if(it.isBlank()) it else doubleOrNull?.format() ?: "0"
//                },
//                maxLines = 1,

    MyFinancesTheme {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val double = rememberSaveable { mutableStateOf(0.0) }
            val text = rememberSaveable { mutableStateOf(double.value.format()) }
            val state = rememberTextFieldState()

            TextField(
                state = state,
                label = { Text(text = stringResource(R.string.price)) },
                lineLimits = TextFieldLineLimits.SingleLine,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                outputTransformation = OutputTransformation {
                    val d = originalText.toString().toDoubleOrNull()
                    
                    if(d != null) {

                    }
                }
            )
        }
    }
}**/