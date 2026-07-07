package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id

fun interface GetByParent {
    suspend fun getByParent(parentId: Id): List<FolderDto>?
}
