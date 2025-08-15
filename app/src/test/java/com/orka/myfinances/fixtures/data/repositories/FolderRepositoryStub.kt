package com.orka.myfinances.fixtures.data.repositories

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.repositories.AddFolderRequest
import com.orka.myfinances.data.repositories.FolderRepository
import com.orka.myfinances.testLib.id

class FolderRepositoryStub : FolderRepository {
    override suspend fun get(): List<Folder>? {
        return emptyList()
    }

    override suspend fun add(request: AddFolderRequest): Folder? {
        return Catalog(id, "name", emptyList())
    }
}