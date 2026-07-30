package com.orka.myfinances.ui.screens.client.details

interface ClientInteractor {
    fun back()

    companion object {
        val dummy = object : ClientInteractor {
            override fun back() {}
        }
    }
}