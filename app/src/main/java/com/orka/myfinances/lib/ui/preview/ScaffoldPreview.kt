package com.orka.myfinances.lib.ui.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.ui.theme.MyFinancesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldPreview(
    modifier: Modifier = Modifier.Companion,
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    MyFinancesTheme {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(title = { Text(text = title) })
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}