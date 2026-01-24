package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.lib.data.repositories.GetRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class SaleContentViewModel(
    loading: Int,
    failure: Int,
    repository: GetRepository<Sale>,
    logger: Logger
) : ListViewModel<Int, Sale, Int>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger
)