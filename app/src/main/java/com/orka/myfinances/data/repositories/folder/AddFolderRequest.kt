package com.orka.myfinances.data.repositories.folder

data class AddFolderRequest(
    val name: String,
    val type: FolderType
)