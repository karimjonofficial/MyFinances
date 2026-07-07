package com.orka.myfinances.ui.screens.profile

import com.orka.myfinances.lib.ui.viewmodel.StateFul
import com.orka.myfinances.ui.screens.profile.models.BranchItemModel

interface ProfileInteractor : StateFul {
    fun debts()
    fun orders()
    fun clients()
    fun templates()
    fun history()
    fun settings()
    fun setBranch(branch: BranchItemModel)
    fun logout()

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
            override fun setBranch(branch: BranchItemModel) {}
            override fun logout() {}
        }
    }
}