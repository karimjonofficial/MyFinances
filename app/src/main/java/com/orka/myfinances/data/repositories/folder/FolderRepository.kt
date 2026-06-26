package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import kotlinx.coroutines.flow.MutableSharedFlow

class FolderRepository(
    private val officeId: Id,
    private val flow: MutableSharedFlow<FolderEvent>,
    private val api: FolderApi
) : Get<FolderDto>, GetTop, Add<FolderDto, AddFolderRequest>, GetById<FolderDto> {

    override suspend fun getTop(query: String?): List<FolderDto>? {
        return api.getTop(
            officeId = officeId.value,
            search = query
        )?.map { it.toDto() }
    }

    override suspend fun add(request: AddFolderRequest): FolderDto? {
        val response = api.add(request.map(officeId))?.toDto()
        if(response != null)
            flow.emit(FolderEvent(request.parentId))
        return response
    }

    suspend fun getByParent(parentId: Id): List<FolderDto>? {
        return api.getByParent(
            officeId = officeId.value,
            parentId = parentId.value
        )?.map { it.toDto() }
    }

    override suspend fun getById(id: Id): FolderDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun getAll(search: String?): List<FolderDto>? {
        return api.get(
            officeId = officeId.value,
            search = search
        )?.map { it.toDto() }
    }
}