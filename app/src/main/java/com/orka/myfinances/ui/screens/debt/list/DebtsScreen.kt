package com.orka.myfinances.ui.screens.debt.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
    dialogVisible: Boolean,
    interactor: DebtsScreenInteractor,
    onAddDebt: () -> Unit,
    dialog: @Composable () -> Unit
) {
    LazyColumnWithStickyHeaderScreen(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.debts)) },
                actions = {
                    IconButton(onClick = onAddDebt) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = stringResource(R.string.add)
                        )
                    }
                }
            )
        },
        refresh = interactor::refresh,
        state = state,
        item = { item ->
            DebtCard(
                debt = item.model,
                onClick = { interactor.select(item) }
            )
        },
        dialogVisible = dialogVisible,
        dialog = dialog
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
        dialogVisible = false,
        interactor = DebtsScreenInteractor.dummy,
        onAddDebt = {},
        dialog = {}
    )
}