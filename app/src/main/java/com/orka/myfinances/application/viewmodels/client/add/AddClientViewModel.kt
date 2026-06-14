package com.orka.myfinances.application.viewmodels.client.add

import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.client.toApiRequest
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.data.repositories.client.ClientEvent
import com.orka.myfinances.lib.data.api.scoped.company.insert
import com.orka.myfinances.lib.viewmodel.Manager
import kotlinx.coroutines.flow.MutableSharedFlow

class AddClientViewModel(
    private val clientApi: ClientApi,
    private val flow: MutableSharedFlow<ClientEvent>
) : Manager() {
    fun add(
        name: String,
        lastName: String?,
        patronymic: String?,
        phone: String?,
        address: String?
    ) {
        launch {
            val request = AddClientRequest(
                firstName = name,
                lastName = lastName,
                patronymic = patronymic,
                phone = phone,
                address = address
            )
            val created = clientApi.insert(
                request = request,
                map = AddClientRequest::toApiRequest
            )
            if (created) flow.emit(ClientEvent)
        }
    }
}