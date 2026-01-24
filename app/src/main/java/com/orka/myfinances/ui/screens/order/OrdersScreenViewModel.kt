package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.lib.data.repositories.GetRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope

class OrdersScreenViewModel(
    repository: GetRepository<Order>,
    loading: String,
    failure: String,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<String, Order, String>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger,
    coroutineScope = coroutineScope
)