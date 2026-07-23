package com.orka.myfinances.application.data.repositories

import com.orka.myfinances.data.database.daos.DefaultsDao
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.data.repositories.defaults.SetDefaultCategory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultsRepository(private val dao: DefaultsDao) : GetDefaultCategory, SetDefaultCategory {
    private val events = MutableSharedFlow<DefaultsEvent>()
    val flow = events.asFlow()

    override suspend fun getDefaultCategoryId(): Id? {
        val id = dao.getDefaultCatalogId()
        return if(id != null) Id(id) else null
    }

    override suspend fun setDefaultCategoryId(id: Id) {
        if(getDefaultCategoryId() == null)
            dao.insertDefaultCatalogId(id.value)
        else dao.setDefaultCatalogId(id.value)
        events.emit(DefaultsEvent.Category)
    }
}