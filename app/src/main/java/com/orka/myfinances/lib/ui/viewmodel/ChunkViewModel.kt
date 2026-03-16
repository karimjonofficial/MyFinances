package com.orka.myfinances.lib.ui.viewmodel

interface ChunkViewModel<T> : SingleStateViewModel<State> {
    fun loadMore()
}