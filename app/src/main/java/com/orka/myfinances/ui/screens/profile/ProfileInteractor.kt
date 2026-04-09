package com.orka.myfinances.ui.screens.profile

import com.orka.myfinances.lib.ui.viewmodel.StateFul
import com.orka.myfinances.ui.screens.profile.models.OfficeItemModel

interface ProfileInteractor : StateFul {
    fun debts()
    fun orders()
    fun clients()
    fun templates()
    fun history()
    fun settings()
    fun setOffice(office: OfficeItemModel)

    companion object {
        val dummy = object : ProfileInteractor {
            override fun initialize() {}
            override fun refresh() {}
            override fun debts() {}
            override fun orders() {}
            override fun clients() {}
            override fun templates() {}
            override fun history() {}
            override fun settings() {}
            override fun setOffice(office: OfficeItemModel) {}
        }
    }
}