package com.orka.myfinances.ui.screens.receive.list.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface ReceiveContentInteractor : StateFul, ChunkViewModel {
    fun select(receive: ReceiveUiModel)

    companion object {
        val dummy = object : ReceiveContentInteractor {
            override fun initialize() {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun select(receive: ReceiveUiModel) {}
        }
    }
}