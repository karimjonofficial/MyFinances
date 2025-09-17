package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.folder.Folder

interface FolderRepository {
    suspend fun get(): List<Folder>?
    suspend fun add(request: AddFolderRequest): Folder?
}