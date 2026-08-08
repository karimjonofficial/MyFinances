package com.orka.myfinances.ui.screens.settings.main

interface SettingsScreenInteractor {
    fun refresh()
    fun toSelectDefaultCategory()
    fun toPinnedCategories()
    fun toPrinters()
    fun toDefaultPrinter()

    companion object {
        val dummy = object : SettingsScreenInteractor {
            override fun refresh() {}
            override fun toSelectDefaultCategory() {}
            override fun toPinnedCategories() {}
            override fun toPrinters() {}
            override fun toDefaultPrinter() {}
        }
    }
}