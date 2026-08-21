package com.orka.myfinances.data.repositories.defaults

sealed interface DefaultsEvent {
    data object Category : DefaultsEvent
    data object Printer : DefaultsEvent
    data object Template : DefaultsEvent
    data object Client : DefaultsEvent
}
