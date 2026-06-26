package com.orka.myfinances.application.viewmodels.client.add

import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.lib.viewmodel.Manager

class AddClientViewModel(private val repository: ClientRepository) : Manager() {
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
            repository.insert(request)
        }
    }
}