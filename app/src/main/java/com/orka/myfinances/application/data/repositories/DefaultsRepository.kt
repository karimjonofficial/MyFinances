package com.orka.myfinances.application.data.repositories

import com.orka.myfinances.data.database.daos.DefaultsDao
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.data.repositories.defaults.SetDefaultCategory
import com.orka.myfinances.data.repositories.preferences.categories.AddPinnedCategoryRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultsRepository(
    private val defaultsDao: DefaultsDao,
    private val pinnedCategoriesRepository: PinnedCategoriesRepository
) : GetDefaultCategory, SetDefaultCategory {
    private val events = MutableSharedFlow<DefaultsEvent>()
    val flow = events.asFlow()

    override suspend fun getDefaultCategoryId(): Id? {
        val id = defaultsDao.getDefaultCatalogId()
        return if(id != null) Id(id) else null
    }

    override suspend fun setDefaultCategoryId(id: Id) {
        if(getDefaultCategoryId() == null)
            defaultsDao.insertDefaultCatalogId(id.value)
        else defaultsDao.setDefaultCatalogId(id.value)
        pinnedCategoriesRepository.add(AddPinnedCategoryRequest(id))
        events.emit(DefaultsEvent.Category)
    }
}