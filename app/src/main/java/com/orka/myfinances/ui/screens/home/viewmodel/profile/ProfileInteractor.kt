package com.orka.myfinances.ui.screens.home.viewmodel.profile

interface ProfileInteractor {
    fun initialize()
    fun debts()
    fun orders()
    fun clients()
    fun templates()
    fun history()
    fun settings()
}