package com.orka.myfinances.ui.navigation.destination

sealed interface SettingsDestinations : Destination {
    data object Main : SettingsDestinations
    data object Printer : SettingsDestinations
}