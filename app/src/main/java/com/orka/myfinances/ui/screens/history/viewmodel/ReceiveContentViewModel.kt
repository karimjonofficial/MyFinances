package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class ReceiveContentViewModel(
    repository: Get<Receive>,
    loading: Int,
    failure: Int,
    logger: Logger
) : ListViewModel<Int, Receive, Int>(
    repository = repository,
    loading = loading,
    failure = failure,
    logger = logger
)