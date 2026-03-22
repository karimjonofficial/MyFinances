package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.lib.ui.screens.ChunkMapState

interface ChunkViewModel<T> : SingleStateViewModel<State<ChunkMapState<T>>> {
    fun loadMore()
}