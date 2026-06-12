package com.orka.myfinances.ui.screens.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.orka.myfinances.ui.models.ClientItemModel

class CheckoutUIState {
    var exposed by mutableStateOf(false)
    var price by mutableStateOf<Int?>(null)
    var description by mutableStateOf<String?>(null)
    var printReceipt by mutableStateOf(true)
    var selectedClient by mutableStateOf<ClientItemModel?>(null)

    var newClientFirstName by mutableStateOf<String?>(null)
    var newClientLastName by mutableStateOf<String?>(null)
    var newClientPatronymic by mutableStateOf<String?>(null)
    var newClientPhone by mutableStateOf<String?>(null)
    var newClientAddress by mutableStateOf<String?>(null)

    fun clearNewClient() {
        newClientFirstName = null
        newClientLastName = null
        newClientPatronymic = null
        newClientPhone = null
        newClientAddress = null
    }

    fun selectClient(client: ClientItemModel?) {
        selectedClient = client
        if (client != null) clearNewClient()
    }
}
