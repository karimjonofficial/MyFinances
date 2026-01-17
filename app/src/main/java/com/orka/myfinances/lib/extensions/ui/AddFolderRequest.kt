package com.orka.myfinances.lib.extensions.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderType
import com.orka.myfinances.fixtures.resources.models.template.template1

fun AddFolderRequest.toModel(id: Id): Folder {
    val folder = if(this.type is FolderType.Catalog) {
        Catalog(
            id = id,
            name = this.name
        )
    } else {
        Category(
            id = id,
            name = this.name,
            template = template1
        )
    }
    return folder
}