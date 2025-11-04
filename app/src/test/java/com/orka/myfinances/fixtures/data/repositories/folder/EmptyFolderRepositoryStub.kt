package com.orka.myfinances.fixtures.data.repositories.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderRepository

class EmptyFolderRepositoryStub : FolderRepository {
    override suspend fun get(): List<Folder>? {
        return null
    }

    override suspend fun get(id: Id): List<Folder>? {
        return null
    }

    override suspend fun add(request: AddFolderRequest): Folder? {
        return null
    }
}