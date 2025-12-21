package com.orka.myfinances.ui.navigation

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope

class OrdersScreenViewModel(
    repository: OrderRepository,
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