package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.application.viewmodels.client.bottomsheet.ClientBottomSheetViewModel
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenModel
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CheckoutScreen(
    modifier: Modifier,
    interactor: CheckoutScreenInteractor,
    state: State<CheckoutScreenModel>,
    clientSheetViewModel: ClientBottomSheetViewModel
) {
    val price =
        rememberSaveable { mutableStateOf(if (state is State.Success) state.value.price else null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val printReceipt = rememberSaveable { mutableStateOf(state is State.Success) }
    val selectedClient = retain { mutableStateOf<ClientItemModel?>(null) }
    val sheetVisible = rememberSaveable { mutableStateOf(false) }
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val clientSheetState = clientSheetViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        clientSheetViewModel.initialize()
    }

    StatefulScreen(
        modifier = modifier,
        title = stringResource(R.string.checkout),
        bottomBar = {
            if (it is State.Success) {
                BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    val priceValue = price.value

                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        enabled = selectedClient.value != null && priceValue != null,
                        onClick = {
                            interactor.order(
                                price = priceValue,
                                description = description.value,
                                clientId = selectedClient.value!!.id
                            )
                        }
                    ) {
                        Text(text = stringResource(R.string.order))
                    }

                    HorizontalSpacer(8)
                    Button(
                        enabled = selectedClient.value != null && priceValue != null,
                        onClick = {
                            interactor.sell(
                                price = priceValue,
                                description = description.value,
                                clientId = selectedClient.value!!.id,
                                print = printReceipt.value
                            )
                        }
                    ) {
                        Text(text = stringResource(R.string.sell))
                    }
                }
            }
        },
        state = state,
        onRetry = interactor::refresh
    ) { modifier, state ->
        CheckoutContent(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp),
            items = state.items,
            price = state.price,
            selectedClient = selectedClient.value,
            description = description.value,
            printReceipt = printReceipt.value,
            onOpenClients = { sheetVisible.value = true },
            onPriceChanged = { price.value = it },
            onDescriptionChanged = { description.value = it },
            onPrintReceiptChanged = { printReceipt.value = it }
        )
    }

    if (sheetVisible.value) {
        SelectClientBottomSheet(
            state = clientSheetState.value,
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        sheetVisible.value = false
                    }
                }
            },
            onSelected = {
                selectedClient.value = it
                coroutineScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        sheetVisible.value = false
                    }
                }
            },
            onLoadMore = clientSheetViewModel::loadMore
        )
    }
}
