package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.folder.Folder

fun interface GetTopFolders {
    suspend fun getTop(): List<Folder>?
}