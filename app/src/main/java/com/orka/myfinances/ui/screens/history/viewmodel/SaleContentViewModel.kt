package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.lib.data.repositories.GetRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope

class SaleContentViewModel(
    loading: String,
    failure: String,
    repository: GetRepository<Sale>,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<String, Sale, String>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger,
    coroutineScope = coroutineScope
)