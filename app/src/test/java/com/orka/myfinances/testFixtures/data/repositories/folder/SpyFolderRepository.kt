package com.orka.myfinances.testFixtures.data.repositories.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.lib.extensions.ui.toModel

class SpyFolderRepository : FolderRepository {
    val folders: MutableList<Folder> = mutableListOf()
    var id: Id? = null

    override suspend fun get(): List<Folder> {
        return folders
    }

    override suspend fun get(id: Id): List<Folder> {
        this.id = id
        return folders
    }

    override suspend fun add(request: AddFolderRequest): Folder {
        return request.toModel(id1)
    }
}