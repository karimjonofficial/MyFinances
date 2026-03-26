package com.orka.myfinances.ui.screens.product.list

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.flow.StateFlow

interface ProductTitlesContentInteractor : ChunkViewModel<ProductTitleUiModel> {
    fun selectProduct(id: Id)

    companion object {
        val dummy = object : ProductTitlesContentInteractor {
            override val uiState: StateFlow<State<ChunkMapState<ProductTitleUiModel>>>
                get() = TODO("Not yet implemented")

            override fun selectProduct(id: Id) {}
            override fun loadMore() {}
            override fun initialize() {}
        }
    }
}
