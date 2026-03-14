package com.orka.myfinances.ui.screens.client.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.application.viewmodels.client.list.ClientsScreenViewModel
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.components.ClientCard
import com.orka.myfinances.ui.theme.MyFinancesTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(
    modifier: Modifier,
    state: State,
    viewModel: ClientsScreenViewModel
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    LazyColumnScreen(
        modifier = modifier,
        viewModel = viewModel,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.clients),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
        dialogState = dialogVisible,
        dialog = {
            AddClientDialog(
                dismissRequest = { dialogVisible.value = false },
                onSuccess = { name, lastName, phone, address ->
                    viewModel.add(name, lastName, phone, address)
                }
            )
        },
        arrangementSpace = 8.dp,
        item = { modifier, client ->
            ClientCard(
                modifier = modifier,
                model = client.model,
                onClick = { viewModel.select(client) }
            )
        }
    )
}

@Preview
@Composable
private fun ClientsScreenPreview() {
    val client = HttpClient(OkHttp)
    val viewModel = viewModel {
        ClientsScreenViewModel(
            clientApi = ClientApi(client),
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            navigator = DummyNavigator(),
            logger = DummyLogger()
        )
    }

    MyFinancesTheme {
        ClientsScreen(
            modifier = Modifier,
            viewModel = viewModel,
            state = State.Success(clients.map { it.toUiModel() })
        )
    }
}