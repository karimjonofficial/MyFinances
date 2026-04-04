package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.data.api.folder.models.response.CatalogApiModel
import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.data.api.folder.toEntity
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template

fun CategoryApiModel.toEntity(template: Template): Category {
    return Category(
        id = Id(id),
        name = name,
        template = template
    )
}

fun FolderApiModel.toEntity(): Folder {
    return when(this) {
        is CatalogApiModel -> toEntity()
        is CategoryApiModel -> toEntity(template.toEntity())
    }
}