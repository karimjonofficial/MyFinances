package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.fixtures.resources.models.folder.categories
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository

class CategoryRepository(private val folderRepository: FolderRepository) : Get<Category>, MockGetByIdRepository<Category> {
    override val items = categories.toMutableList()

    override suspend fun List<Category>.find(id: Id): Category? {
        return this.find { it.id == id }
    }

    override suspend fun get(): List<Category>? {
        return folderRepository.get()?.filter { it is Category }?.map { it as Category }
    }
}