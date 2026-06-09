package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id

sealed interface BasketEvent {
    data class AmountChanged(val id: Id, val newAmount: Int) : BasketEvent
    data class ItemRemoved(val id: Id) : BasketEvent
    data object Clear : BasketEvent
    data object FullRefresh : BasketEvent
}
