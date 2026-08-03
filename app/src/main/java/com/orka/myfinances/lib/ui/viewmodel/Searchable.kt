package com.orka.myfinances.lib.ui.viewmodel

interface Searchable {
    fun search(query: String)
    fun resetSearch()
}