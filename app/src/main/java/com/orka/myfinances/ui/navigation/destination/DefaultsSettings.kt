package com.orka.myfinances.ui.navigation.destination

sealed interface DefaultsSettings : SettingsDestinations {
    data object Category : DefaultsSettings
    data object Printer : DefaultsSettings
    data object Template : DefaultsSettings
}
