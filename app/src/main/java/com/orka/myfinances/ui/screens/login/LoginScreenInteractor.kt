package com.orka.myfinances.ui.screens.login

interface LoginScreenInteractor {
    fun authorize(username: String, password: String)
    fun authorizeAndRemember(username: String, password: String)

    companion object {
        val dummy = object : LoginScreenInteractor {
            override fun authorize(username: String, password: String) {}
            override fun authorizeAndRemember(username: String, password: String) {}
        }
    }
}