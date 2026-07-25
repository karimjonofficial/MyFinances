package com.orka.myfinances.ui.resources

import com.orka.myfinances.fixtures.resources.models.stockItems
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.viewmodel.State
import com.orka.myfinances.ui.map.toMap

val stockItemsState = State.Success(
    value = ChunkUiModel(
        content = stockItems.toMap(),
        count = 1,
        pageIndex = 1,
        nextPageIndex = 2,
        previousPageIndex = null,
    )
)