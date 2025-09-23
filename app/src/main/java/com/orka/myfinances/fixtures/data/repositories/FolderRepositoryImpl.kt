package com.orka.myfinances.fixtures.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.folder.FolderType
import com.orka.myfinances.fixtures.resources.models.template.template1

class FolderRepositoryImpl : FolderRepository {
    private val folders = com.orka.myfinances.fixtures.resources.models.folder.folders.toMutableList()

    override suspend fun get(): List<Folder>? {
        return folders.toList()
    }

    override suspend fun add(request: AddFolderRequest): Folder {
        val folder = if(request.type is FolderType.Catalog) {
            Catalog(
                id = Id(1),
                name = request.name,
                folders = emptyList()
            )
        } else {
            Warehouse(
                id = Id(1),
                name = request.name,
                template = template1,
                products = emptyList(),
                stockItems = emptyList()
            )
        }
        folders.add(folder)
        return folder
    }
}