package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.lib.data.repositories.GetRepository

class CategoryRepository(private val folderRepository: FolderRepository) : GetRepository<Category> {
    override suspend fun get(): List<Category>? {
        return folderRepository.get()?.filter { it is Category }?.map { it as Category }
    }
}