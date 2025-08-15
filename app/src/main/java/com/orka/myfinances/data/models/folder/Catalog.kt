package com.orka.myfinances.data.models.folder

import com.orka.myfinances.data.models.Id

data class Catalog(
    override val id: Id,
    override val name: String,
    val folders: List<Folder>?
) : Folder