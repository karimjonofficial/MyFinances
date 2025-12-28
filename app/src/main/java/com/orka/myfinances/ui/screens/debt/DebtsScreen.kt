package com.orka.myfinances.ui.screens.debt

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.debt.components.DebtCard
import com.orka.myfinances.ui.screens.debt.parts.AddDebtDialog
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsScreen(
    modifier: Modifier,
    viewModel: DebtScreenViewModel,
    navigationManager: NavigationManager
) {
    val visible = rememberSaveable { mutableStateOf(false) }

    LazyColumnScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.debts)) },
                actions = {
                    IconButton(onClick = { visible.value = true }) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = stringResource(R.string.add)
                        )
                    }
                }
            )
        },
        viewModel = viewModel,
        item = { modifier, item ->

            DebtCard(
                modifier = modifier,
                debt = item,
                onClick = { navigationManager.navigateToDebt(it) }
            )
        },
        dialogState = visible,
        dialog = {
            val dialogState = viewModel.dialogState.collectAsState()

            AddDebtDialog(
                state = dialogState.value,
                dismissRequest = { visible.value = false },
                onSuccess = {
                    viewModel.add(it)
                    visible.value = false
                },
                onCancel = { visible.value = false }
            )
        }
    )
}