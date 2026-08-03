package com.orka.myfinances.lib.ui.viewmodel

interface PaginatedSearchable : Paginated, Searchable {
    fun searchMore()
}
