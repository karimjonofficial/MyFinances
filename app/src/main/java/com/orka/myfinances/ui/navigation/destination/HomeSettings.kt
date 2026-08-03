package com.orka.myfinances.ui.navigation.destination

sealed interface HomeSettings : SettingsDestinations {
    data object PinnedCategories : HomeSettings
}
