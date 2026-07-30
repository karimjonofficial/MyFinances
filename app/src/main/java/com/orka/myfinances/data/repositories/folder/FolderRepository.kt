package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import kotlinx.coroutines.flow.MutableSharedFlow

class FolderRepository(
    private val branchId: Id,
    private val flow: MutableSharedFlow<FolderEvent>,
    private val api: FolderApi
) : Get<FolderDto>, GetTop, Add<Unit, AddFolderRequest>, GetById<FolderDto>, GetByParent, GetCategories {

    override suspend fun getTop(): List<FolderDto>? {
        return api.getTop(branchId.value)?.map { it.toDto() }
    }

    override suspend fun add(request: AddFolderRequest) {
        val response = api.add(request.toApiRequest(branchId))
        if(response != null)
            flow.emit(FolderEvent(request.parentId))
    }

    override suspend fun getByParent(parentId: Id): List<FolderDto>? {
        return api.getByParent(
            branchId = branchId.value,
            parentId = parentId.value
        )?.map { it.toDto() }
    }

    override suspend fun getById(id: Id): FolderDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun getAll(): List<FolderDto>? {
        return api.get(branchId.value)?.map { it.toDto() }
    }

    override suspend fun getCategories(): List<CategoryDto>? {
        return api.get(branchId.value)?.filter { !it.isCatalog }?.map { it.toDto() as CategoryDto }
    }
}
