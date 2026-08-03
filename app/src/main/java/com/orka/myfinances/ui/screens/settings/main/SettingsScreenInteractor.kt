package com.orka.myfinances.ui.screens.settings.main

interface SettingsScreenInteractor {
    fun refresh()
    fun toSelectDefaultCategory()
    fun toPinnedCategories()
    fun toPrinters()

    companion object {
        val dummy = object : SettingsScreenInteractor {
            override fun refresh() {}
            override fun toSelectDefaultCategory() {}
            override fun toPinnedCategories() {}
            override fun toPrinters() {}
        }
    }
}