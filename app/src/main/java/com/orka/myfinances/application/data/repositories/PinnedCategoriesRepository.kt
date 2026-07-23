package com.orka.myfinances.application.data.repositories

import com.orka.myfinances.data.database.daos.PinnedCategoriesDao
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.preferences.categories.AddPinnedCategoryRequest
import com.orka.myfinances.data.repositories.preferences.categories.PinnedCategoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow

@OptIn(ExperimentalCoroutinesApi::class)
class PinnedCategoriesRepository(private val dao: PinnedCategoriesDao) : PinnedCategoriesRepository {
    private val flow = MutableSharedFlow<PinnedCategoriesEvent>()
    val events = flow.asFlow()

    override suspend fun getAll(search: String?): List<Id>? {
        val categories = dao.getAll()
        return if (categories.isEmpty()) null
        else categories.map { Id(it.id) }
    }

    override suspend fun add(request: AddPinnedCategoryRequest) {
        if (dao.getAll().none { it.id == request.id.value }) {
            dao.insert(
                id = request.id.value,
                index = request.index ?: ((dao.getLastIndex() ?: -1) + 1)
            )
            flow.emit(PinnedCategoriesEvent)
        }
    }

    override suspend fun remove(id: Id) {
        if(dao.getAll().any { it.id == id.value }) {
            dao.delete(id.value)
            flow.emit(PinnedCategoriesEvent)
        }
    }
}