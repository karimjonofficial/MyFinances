package com.orka.myfinances.data.repositories

data class AddFolderRequest(
    val name: String,
    val type: FolderType
)