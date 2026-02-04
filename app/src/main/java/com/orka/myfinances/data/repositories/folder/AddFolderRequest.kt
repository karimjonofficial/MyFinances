package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.Id

data class AddFolderRequest(
    val name: String,
    val type: String,
    val templateId: Id? = null,
    val parentId: Id? = null
)