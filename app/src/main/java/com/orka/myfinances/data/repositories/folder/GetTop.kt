package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.dtos.folder.FolderDto

fun interface GetTop {
    suspend fun getTop(query: String?): List<FolderDto>?
}