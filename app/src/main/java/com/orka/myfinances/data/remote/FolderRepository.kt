package com.orka.myfinances.data.remote

import com.orka.myfinances.data.api.folder.CatalogModel
import com.orka.myfinances.data.api.folder.CategoryModel
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.lib.data.repositories.GetById
import kotlinx.coroutines.flow.MutableSharedFlow

class FolderRepository(
    private val api: FolderApi,
    private val office: Office,
    private val getById: GetById<Template>
) : FolderRepository {
    private val flow = MutableSharedFlow<FolderEvent>()
    override val events = flow

    override suspend fun get(): List<Folder>? {
        return api.get(office.id.value)?.map()
    }

    override suspend fun get(parameter: Catalog): List<Folder>? {
        return api.getChildren(parameter.id.value, office.id.value)?.map()
    }

    override suspend fun add(request: AddFolderRequest): Folder? {
        return api.add(request)?.map()
    }

    override suspend fun getTop(): List<Folder>? {
        return api.getTop(office.id.value)?.map {
            when(it) {
                is CategoryModel -> {
                    val template = getById.getById(Id(it.template))
                    if (template != null) it.map(template) else null
                }
                is CatalogModel -> it.map()
            } as Folder
        }
    }
}