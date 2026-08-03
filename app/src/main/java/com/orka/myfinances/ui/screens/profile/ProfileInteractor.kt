package com.orka.myfinances.ui.screens.profile

import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.sheet.BranchItemModel

interface ProfileInteractor : Refreshable {
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