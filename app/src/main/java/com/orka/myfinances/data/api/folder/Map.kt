package com.orka.myfinances.data.api.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.fixtures.resources.models.template.template1

fun AddFolderRequest.map(): AddFolderApiRequest {
    return AddFolderApiRequest(
        name = name,
        type = type,
        templateId = templateId?.value,
        parentId = parentId?.value
    )
}

fun CategoryModel.map(template: Template): Category {
    return Category(
        id = Id(id),
        name = name,
        template = template
    )
}

fun CatalogModel.map(): Catalog {
    return Catalog(
        id = Id(id),
        name = name
    )
}

fun List<FolderModel>.map(): List<Folder> {
    return map { it.map() }
}

fun FolderModel.map(): Folder {
    return when(this) {
        is CatalogModel -> map()
        is CategoryModel -> map(template1)//TODO
    }
}