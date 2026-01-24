package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold

@Composable
fun BlankScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.title),
    content: String? = null
) {
    Scaffold(
        modifier = modifier,
        title = title,
    ) { paddingValues ->
        Box(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if(content?.isNotBlank() ?: false) {
                Text(text = content)
            }
        }
    }
}