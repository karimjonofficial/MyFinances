package com.orka.myfinances.ui.screens.templates

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.screens.templates.viewmodel.TemplatesScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplatesScreen(
    modifier: Modifier = Modifier,
    viewModel: TemplatesScreenViewModel,
    navigator: Navigator
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.templates)) },
                actions = {
                    IconButton(onClick = { navigator.navigateToAddTemplate() }) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = stringResource(R.string.add)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val uiState = viewModel.uiState.collectAsState()

        TemplatesContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = uiState.value,
            navigator = navigator
        )
    }
}