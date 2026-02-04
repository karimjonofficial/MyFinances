package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.Text
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class OrdersScreenViewModel(
    repository: Get<Order>,
    loading: Text,
    failure: Text,
    logger: Logger
) : ListViewModel<Order>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger
)