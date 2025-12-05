package com.orka.myfinances.ui.screens.clients

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.repositories.ClientRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.data.models.Client
import kotlinx.coroutines.CoroutineScope

class ClientsScreenViewModel(
    loading: String,
    failure: String,
    repository: ClientRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<String, Client, String>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger,
    coroutineScope = coroutineScope
)