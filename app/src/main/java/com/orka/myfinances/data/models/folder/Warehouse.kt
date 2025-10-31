package com.orka.myfinances.data.models.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template

data class Warehouse(
    override val id: Id,
    override val name: String,
    val template: Template
) : Folder