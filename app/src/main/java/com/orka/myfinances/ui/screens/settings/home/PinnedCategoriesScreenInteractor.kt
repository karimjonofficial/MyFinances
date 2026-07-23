package com.orka.myfinances.ui.screens.settings.home

interface PinnedCategoriesScreenInteractor {
    fun save(ids: List<Int>)

    companion object {
        val dummy = object : PinnedCategoriesScreenInteractor {
            override fun save(ids: List<Int>) {}
        }
    }
}