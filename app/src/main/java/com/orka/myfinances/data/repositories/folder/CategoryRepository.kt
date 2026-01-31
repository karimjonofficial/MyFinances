package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.lib.data.repositories.Get

class CategoryRepository(private val folderRepository: FolderRepository) : Get<Category> {
    override suspend fun get(): List<Category>? {
        return folderRepository.get()?.filter { it is Category }?.map { it as Category }
    }
}