package com.orka.myfinances.data.repositories.basket

sealed interface BasketEvent {
    object Clear : BasketEvent
}