package com.orka.myfinances.ui.screens.debt

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.format.FormatDateImpl
import com.orka.myfinances.fixtures.format.FormatDateTimeImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.debts
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.debt.components.DebtCard
import com.orka.myfinances.ui.screens.debt.parts.AddDebtDialog
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtsScreenViewModel
import com.orka.myfinances.ui.screens.debt.viewmodel.toMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsScreen(
    modifier: Modifier,
    state: State,
    viewModel: DebtsScreenViewModel
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
        item = { modifier, item ->
            DebtCard(
                modifier = modifier,
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

@Preview
@Composable
private fun DebtsScreenPreview() {
    val viewModel = viewModel {
        DebtsScreenViewModel(
            getDebts = { null },
            add = { null },
            getClients = { null },
            formatPrice = { "" },
            formatDate = { "" },
            formatDateTime = { "" },
            loading = UiText.Str("Loading"),
            failure = UiText.Str("Failure"),
            logger = DummyLogger(),
            navigator = DummyNavigator()
        )
    }
    val data = debts.toMutableList()
    data.addAll(debts)
    data.addAll(debts)
    data.addAll(debts)
    data.addAll(debts)
    data.addAll(debts)
    data.addAll(debts)
    data.addAll(debts)
    data.addAll(debts)
    data.addAll(debts)
    val state = State.Success(
        value = data.toMap(
            formatPrice = FormatPriceImpl(),
            formatDate = FormatDateImpl(),
            formatDateTime = FormatDateTimeImpl()
        )
    )

    DebtsScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        viewModel = viewModel
    )
}
