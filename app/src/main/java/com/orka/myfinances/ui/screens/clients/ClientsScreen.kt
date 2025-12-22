package com.orka.myfinances.ui.screens.clients

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
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.fixtures.managers.DummyNavigationManager
import com.orka.myfinances.lib.LoggerImpl
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(
    modifier: Modifier,
    destination: Destination.Clients,
    navigationManager: NavigationManager
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val viewModel = destination.viewModel as ClientsScreenViewModel

    LazyColumnScreen(
        modifier = modifier,
        viewModel = viewModel,
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
        dialogState = dialogVisible,
        dialog = {
            AddClientDialog(
                dismissRequest = { dialogVisible.value = false },
                onSuccess = { name, lastName, phone, address ->
                    viewModel.add(name, lastName, phone, address)
                }
            )
        },
        item = { modifier, client ->
            ClientCard(
                modifier = modifier,
                client = client,
                onClick = { navigationManager.navigateToClient(it) }
            )
        }
    )
}

@Preview
@Composable
private fun ClientsScreenPreview() {
    val repository = ClientRepository()
    val viewModel = ClientsScreenViewModel(
        repository = repository,
        loading = "loading",
        failure = "failure",
        logger = LoggerImpl(),
        coroutineScope = CoroutineScope(Dispatchers.Default)
    )

    ClientsScreen(
        modifier = Modifier,
        destination = Destination.Clients(viewModel),
        navigationManager = DummyNavigationManager()
    )
}