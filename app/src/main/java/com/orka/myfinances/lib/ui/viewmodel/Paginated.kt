package com.orka.myfinances.lib.ui.viewmodel

interface Paginated : Refreshable {
    fun loadMore()
}