package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.fixtures.resources.models.folder.folders
import com.orka.myfinances.lib.data.repositories.GetByIdRepository
import com.orka.myfinances.lib.data.repositories.GetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository

class FolderRepository(private val templateRepository: GetByIdRepository<Template>) :
    MockGetRepository<Folder>, MockAddRepository<Folder, AddFolderRequest>,
    GetByParameterRepository<Folder, Catalog> {
    override val items = folders.toMutableList()

    override suspend fun get(parameter: Catalog): List<Folder> {
        return folders
    }

    override suspend fun AddFolderRequest.map(): Folder {
        return when (type) {
            "catalog" -> Catalog(
                id = Id(1),
                name = this.name
            )

            "category" -> {
                val t = templateRepository.getById(templateId!!)
                if (t != null) {
                    Category(
                        id = Id(1),
                        name = this.name,
                        template = t
                    )
                } else throw IllegalArgumentException()
            }

            else -> throw IllegalArgumentException()
        }
    }
}