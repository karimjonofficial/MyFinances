package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Folder

interface FolderRepository {
    suspend fun get(): List<Folder>?
    suspend fun get(id: Id): List<Folder>?
    suspend fun add(request: AddFolderRequest): Folder?
}