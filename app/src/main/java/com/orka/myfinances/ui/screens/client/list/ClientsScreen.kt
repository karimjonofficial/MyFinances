package com.orka.myfinances.ui.screens.client.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.components.ClientCard
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientUiModel
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientsScreenInteractor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(
    modifier: Modifier,
    state: State<ChunkMapState<ClientUiModel>>,
    interactor: ClientsScreenInteractor
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    LazyColumnWithStickyHeaderScreen(
        modifier = modifier,
        refresh = interactor::refresh,
        loadMore = interactor::loadMore,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.clients)) },
                actions = {
                    IconButton(onClick = { dialogVisible.value = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = stringResource(R.string.add)
                        )
                    }
                }
            )
        },
        state = state,
        dialogVisible = dialogVisible.value,
        dialog = {
            AddClientDialog(
                dismissRequest = { dialogVisible.value = false },
                onSuccess = { name, lastName, patronymic, phone, address ->
                    interactor.add(name, lastName, patronymic, phone, address)
                    dialogVisible.value = false
                }
            )
        },
        arrangementSpace = 2.dp,
        item = { client ->
            ClientCard(
                model = client.model,
                onClick = { interactor.select(client) }
            )
        }
    )
}