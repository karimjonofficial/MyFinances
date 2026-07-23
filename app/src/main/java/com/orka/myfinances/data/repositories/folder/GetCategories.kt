package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.dtos.folder.CategoryDto

interface GetCategories {
    suspend fun getCategories(query: String? = null): List<CategoryDto>?
}