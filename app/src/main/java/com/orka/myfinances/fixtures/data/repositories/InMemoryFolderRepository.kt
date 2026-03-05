package com.orka.myfinances.fixtures.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.fixtures.resources.models.folder.folders
import com.orka.myfinances.lib.data.repositories.Generator
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class InMemoryFolderRepository(
    private val getById: GetById<Template>,
    private val generator: Generator<Id>
) : MockGetRepository<Folder>, MockAddRepository<Folder, AddFolderRequest>,
    MockGetByParameterRepository<Folder, Catalog>, FolderRepository {
    private var catalogId: Id? = null
    private val flow = MutableSharedFlow<FolderEvent>()
    override val events: Flow<FolderEvent> = flow
    override val items = folders.toMutableList()

    override suspend fun AddFolderRequest.map(): Folder {
        catalogId = this.parentId

        return when (type) {
            "catalog" -> Catalog(
                id = generator.generate(),
                name = this.name
            )

            "category" -> {
                val t = getById.getById(templateId!!)
                if (t != null) Category(
                    id = generator.generate(),
                    name = this.name,
                    template = t
                )
                else throw IllegalArgumentException()
            }

            else -> throw IllegalArgumentException()
        }
    }

    override suspend fun afterAdd(item: Folder) {
        catalogId?.let {
            if (item is Catalog) {
                flow.emit(FolderEvent(it))
                catalogId = null
            }
        }
    }

    override suspend fun getTop(): List<Folder> {
        return folders.toList()
    }
}