package com.orka.myfinances.data.models.folder

import com.orka.myfinances.data.models.Id

interface Folder {
    val id: Id
    val name: String
}