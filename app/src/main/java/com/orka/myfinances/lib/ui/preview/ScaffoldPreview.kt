package com.orka.myfinances.lib.ui.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun ScaffoldPreview(
    modifier: Modifier = Modifier,
    title: String,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    MyFinancesTheme {
        Scaffold(
            modifier = modifier,
            title = title,
            bottomBar = bottomBar
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}