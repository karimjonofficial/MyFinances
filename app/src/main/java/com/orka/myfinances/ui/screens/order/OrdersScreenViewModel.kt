package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.data.repositories.GetRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class OrdersScreenViewModel(
    repository: GetRepository<Order>,
    loading: Int,
    failure: Int,
    logger: Logger
) : ListViewModel<Int, Order, Int>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger
)