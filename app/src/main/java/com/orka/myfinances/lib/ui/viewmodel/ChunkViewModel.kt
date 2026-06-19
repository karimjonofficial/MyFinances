package com.orka.myfinances.lib.ui.viewmodel

interface ChunkViewModel : StateFul {
    fun loadMore()
    fun search(query: String)
}