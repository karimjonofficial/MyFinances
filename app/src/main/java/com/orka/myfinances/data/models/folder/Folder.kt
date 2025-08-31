package com.orka.myfinances.data.models.folder

import com.orka.myfinances.data.models.Id

sealed interface Folder {
    val id: Id
    val name: String
}