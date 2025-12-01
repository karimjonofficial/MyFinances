package com.orka.myfinances.ui.navigation

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope

class ClientsScreenViewModel(
    loading: String,
    failure: String,
    repository: ClientsRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<String, Client, String>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger,
    coroutineScope = coroutineScope
)