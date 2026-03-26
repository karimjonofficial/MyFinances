package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.flow.MutableStateFlow

interface StockContentInteractor : ChunkViewModel<StockItemUiModel> {
    fun addToBasket(id: Id)

    companion object {
        val dummy = object : StockContentInteractor {
            override val uiState = MutableStateFlow(State.Loading<ChunkMapState<StockItemUiModel>>(UiText.Str("loading")))
            override fun addToBasket(id: Id) {}
            override fun initialize() {}
            override fun loadMore() {}
        }
    }
}
