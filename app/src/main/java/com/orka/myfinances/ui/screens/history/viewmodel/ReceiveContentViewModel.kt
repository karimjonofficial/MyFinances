package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.repositories.ReceiveMockRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope

class ReceiveContentViewModel(
    repository: ReceiveMockRepository,
    loading: String,
    failure: String,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<String, Receive, String>(
    repository = repository,
    loading = loading,
    failure = failure,
    logger = logger,
    coroutineScope = coroutineScope
)