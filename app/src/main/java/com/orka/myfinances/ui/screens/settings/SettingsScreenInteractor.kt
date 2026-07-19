package com.orka.myfinances.ui.screens.settings

interface SettingsScreenInteractor {
    fun toSelectDefaultCategory()

    companion object {
        val dummy = object : SettingsScreenInteractor {
            override fun toSelectDefaultCategory() {}
        }
    }
}