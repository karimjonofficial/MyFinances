package com.orka.myfinances.ui.screens.debt.list

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
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.format.FormatDateImpl
import com.orka.myfinances.fixtures.format.FormatDateTimeImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.resources.models.debts
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.debt.list.interactor.DebtsScreenInteractor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsScreen(
    modifier: Modifier,
    state: State<Map<String, List<DebtUiModel>>>,
    interactor: DebtsScreenInteractor
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
        viewModel = interactor,
        state = state,
        item = { modifier, item ->
            DebtCard(
                modifier = modifier,
                debt = item.model,
                onClick = { interactor.select(item) }
            )
        },
        dialogState = visible,
        dialog = {
            LaunchedEffect(Unit) {
                interactor.initializeClients()
            }
            val dialogState = interactor.dialogState.collectAsState()

            AddDebtDialog(
                state = dialogState.value,
                dismissRequest = { visible.value = false },
                onSuccess = { id, price, endDateTime, description ->
                    interactor.add(id, price, endDateTime, description)
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
    val data = debts.toMutableList()
    repeat(10) { data.addAll(debts) }
    val state = State.Success(
        value = data.map(
            formatPrice = FormatPriceImpl(),
            formatDate = FormatDateImpl(),
            formatDateTime = FormatDateTimeImpl()
        )
    )

    DebtsScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        interactor = DebtsScreenInteractor.dummy
    )
}