package com.orka.myfinances.fixtures.data.repositories.folder

import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.lib.extensions.ui.toModel
import com.orka.myfinances.testLib.id

class SpyFolderRepository : FolderRepository {
    val folders: MutableList<Folder> = mutableListOf()

    override suspend fun get(): List<Folder>? {
        return folders
    }

    override suspend fun add(request: AddFolderRequest): Folder? {
        return request.toModel(id)
    }
}