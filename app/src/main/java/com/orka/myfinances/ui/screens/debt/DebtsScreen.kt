package com.orka.myfinances.ui.screens.debt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.viewmodel.list.State
import com.orka.myfinances.ui.screens.debt.components.DebtCard
import com.orka.myfinances.ui.screens.debt.parts.AddDebtDialog
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsScreen(
    modifier: Modifier,
    state: State,
    viewModel: DebtScreenViewModel
) {
    val visible = rememberSaveable { mutableStateOf(false) }

    LazyColumnWithStickyHeaderScreen(
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
        state = state,
        header = { modifier, date ->
            Text(
                text = date,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        },
        item = { modifier, item ->
            DebtCard(
                modifier = modifier.padding(horizontal = 8.dp),
                debt = item.model,
                onClick = { viewModel.select(item.debt) }
            )
        },
        dialogState = visible,
        dialog = {
            LaunchedEffect(Unit) {
                viewModel.initializeClients()
            }
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
