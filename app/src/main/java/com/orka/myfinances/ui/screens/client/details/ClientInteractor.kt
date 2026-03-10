package com.orka.myfinances.ui.screens.client.details

interface ClientInteractor {
    fun initialize()
    fun back()

    companion object {
        val dummy = object : ClientInteractor {
            override fun initialize() {}
            override fun back() {}
        }
    }
}