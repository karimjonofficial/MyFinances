package com.orka.myfinances.application.data.repositories

import com.orka.myfinances.data.database.daos.PinnedCategoriesDao
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.preferences.categories.AddPinnedCategoryRequest
import com.orka.myfinances.data.repositories.preferences.categories.PinnedCategoriesRepository

class PinnedCategoriesRepository(
    private val pinnedCategoriesDao: PinnedCategoriesDao
) : PinnedCategoriesRepository {

    override suspend fun getAll(search: String?): List<Id>? {
        val categories = pinnedCategoriesDao.getAll()
        return if (categories.isEmpty()) null
        else categories.map { Id(it.id) }
    }

    override suspend fun add(request: AddPinnedCategoryRequest) {
        if (getAll(null)?.none { it.value == request.id.value } ?: true)
            pinnedCategoriesDao.insert(
                id = request.id.value,
                index = request.index ?: ((pinnedCategoriesDao.getLastIndex() ?: -1) + 1)
            )
    }
}