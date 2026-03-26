package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.lib.ui.models.ChunkMapState

interface ChunkViewModel<T> : SingleStateViewModel<State<ChunkMapState<T>>> {
    fun loadMore()
}