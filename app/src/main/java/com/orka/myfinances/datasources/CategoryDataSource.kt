package com.orka.myfinances.datasources

import com.orka.myfinances.models.Category

interface CategoryDataSource {
    suspend fun get(): List<Category>?
}