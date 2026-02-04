package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.fixtures.resources.models.folder.folders
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FolderRepository(private val templateRepository: GetById<Template>) :
    MockGetRepository<Folder>, MockAddRepository<Folder, AddFolderRequest>,
    MockGetByParameterRepository<Folder, Catalog> {
    private var id: Int = folders.maxOfOrNull { it.id.value } ?: 0
    private var catalogId: Id? = null
    private val flow = MutableSharedFlow<FolderEvent>()
    val events: Flow<FolderEvent> = flow
    override val items = folders.toMutableList()

    override suspend fun AddFolderRequest.map(): Folder {
        catalogId = this.parentId

        return when (type) {
            "catalog" -> Catalog(
                    id = Id(id++),
                    name = this.name
                )

            "category" -> {
                val t = templateRepository.getById(templateId!!)
                if (t != null) Category(
                        id = Id(id++),
                        name = this.name,
                        template = t
                    )
                else throw IllegalArgumentException()
            }

            else -> throw IllegalArgumentException()
        }
    }

    override suspend fun afterAdd(item: Folder) {
        if(item is Catalog)
            flow.emit(FolderEvent(catalogId!!))
    }
}