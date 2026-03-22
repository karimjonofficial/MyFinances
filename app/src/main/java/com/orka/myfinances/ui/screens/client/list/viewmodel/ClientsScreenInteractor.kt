package com.orka.myfinances.ui.screens.client.list.viewmodel

import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.ui.viewmodel.StateFul
import kotlinx.coroutines.flow.MutableStateFlow

interface ClientsScreenInteractor : StateFul, ListViewModel<ClientUiModel> {
    fun add(name: String, lastName: String?, patronymic: String?,phone: String?, address: String?)
    fun select(client: ClientUiModel)

    companion object {
        val dummy = object : ClientsScreenInteractor {
            override val uiState = MutableStateFlow(State.Loading<List<ClientUiModel>>(UiText.Str("nothing")))
            override fun add(name: String, lastName: String?, patronymic: String?, phone: String?, address: String?) {}
            override fun select(client: ClientUiModel) {}
            override fun initialize() {}
        }
    }
}