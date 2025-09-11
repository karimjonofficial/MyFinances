package com.orka.myfinances.fixtures.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.data.repositories.AddFolderRequest
import com.orka.myfinances.data.repositories.FolderRepository
import com.orka.myfinances.data.repositories.FolderType
import com.orka.myfinances.fixtures.resources.models.template.template1

class FolderRepositoryImpl : FolderRepository {
    private val folders = mutableListOf<Folder>()

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
            ProductFolder(
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