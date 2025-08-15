package com.orka.myfinances.fixtures.data.repositories

import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.repositories.AddFolderRequest
import com.orka.myfinances.data.repositories.FolderRepository

class EmptyFolderRepositoryStub : FolderRepository {
    override suspend fun get(): List<Folder>? {
        return null
    }

    override suspend fun add(request: AddFolderRequest): Folder? {
        return null
    }
}