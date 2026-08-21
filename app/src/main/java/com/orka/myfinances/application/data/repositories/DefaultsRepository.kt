package com.orka.myfinances.application.data.repositories

import com.orka.myfinances.data.database.daos.DefaultsDao
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.DefaultsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultsRepository(private val dao: DefaultsDao) : DefaultsRepository {
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

    override suspend fun getDefaultPrinter(): Id? {
        val id = dao.getDefaultPrinterId()
        return if(id != null) Id(id) else null
    }

    override suspend fun setDefaultPrinter(id: Id) {
        if(getDefaultPrinter() == null)
            dao.insertDefaultPrinterId(id.value)
        else dao.setDefaultPrinterId(id.value)
        events.emit(DefaultsEvent.Printer)
    }

    override suspend fun getDefaultTemplateId(): Id? {
        val id = dao.getDefaultTemplateId()
        return if(id != null) Id(id) else null
    }

    override suspend fun setDefaultTemplateId(id: Id) {
        if(getDefaultTemplateId() == null)
            dao.insertDefaultTemplateId(id.value)
        else dao.setDefaultTemplateId(id.value)
        events.emit(DefaultsEvent.Template)
    }

    override suspend fun getDefaultClientId(): Id? {
        val id = dao.getDefaultClientId()
        return if(id != null) Id(id) else null
    }

    override suspend fun setDefaultClientId(id: Id) {
        if(getDefaultClientId() == null)
            dao.insertDefaultClientId(id.value)
        else dao.setDefaultClientId(id.value)
        events.emit(DefaultsEvent.Client)
    }
}