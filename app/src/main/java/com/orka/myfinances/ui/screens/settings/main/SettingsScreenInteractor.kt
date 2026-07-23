package com.orka.myfinances.ui.screens.settings.main

interface SettingsScreenInteractor {
    fun toSelectDefaultCategory()
    fun toPinnedCategories()

    companion object {
        val dummy = object : SettingsScreenInteractor {
            override fun toSelectDefaultCategory() {}
            override fun toPinnedCategories() {}
        }
    }
}