package com.orka.myfinances.ui.screens.home.viewmodel.profile

interface ProfileInteractor {
    fun initialize()
    fun debts()
    fun orders()
    fun clients()
    fun templates()
    fun history()
    fun settings()

    companion object {
        val dummy = object : ProfileInteractor {
            override fun initialize() {}
            override fun debts() {}
            override fun orders() {}
            override fun clients() {}
            override fun templates() {}
            override fun history() {}
            override fun settings() {}
        }
    }
}