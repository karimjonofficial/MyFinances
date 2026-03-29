package com.orka.myfinances.ui.screens.debt.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.debt.list.interactor.DebtsScreenInteractor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsScreen(
    modifier: Modifier,
    state: State<ChunkMapState<DebtUiModel>>,
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
        state = state,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        dialogVisible = dialogVisible,
        dialog = dialog,
        item = { item ->
            DebtCard(
                debt = item.model,
                onClick = { interactor.select(item) }
            )
        }
    )
}
