package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class OrdersScreenViewModel(
    repository: Get<Order>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : ListViewModel<Order>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger
)