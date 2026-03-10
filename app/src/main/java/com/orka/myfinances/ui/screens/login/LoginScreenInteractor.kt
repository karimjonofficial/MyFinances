package com.orka.myfinances.ui.screens.login

interface LoginScreenInteractor {
    fun authorize(username: String, password: String)
    fun authorizeAndRemember(username: String, password: String)
}