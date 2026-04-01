package com.orka.myfinances.data.api.folder

import com.orka.myfinances.data.api.folder.models.request.AddFolderApiRequest
import com.orka.myfinances.data.api.folder.models.response.CatalogApiModel
import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.data.api.template.models.response.TemplateApiModelField
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.folder.AddFolderRequest

fun AddFolderRequest.map(officeId: Int): AddFolderApiRequest {
    return AddFolderApiRequest(
        name = name,
        type = type,
        officeId = officeId,
        templateId = templateId?.value,
        parentId = parentId?.value
    )
}

fun CategoryApiModel.map(template: Template): Category {
    return Category(
        id = Id(id),
        name = name,
        template = template
    )
}

fun CatalogApiModel.map(): Catalog {
    return Catalog(
        id = Id(id),
        name = name
    )
}

fun List<FolderApiModel>.map(): List<Folder> {
    return map { it.map() }
}

fun TemplateApiModelField.toEntity(): TemplateField {
    return TemplateField(
        id = Id(id),
        name = name,
        type = type
    )
}

fun TemplateApiModel.map(): Template {
    return Template(
        id = Id(id),
        name = name,
        fields = fields.map { it.toEntity() }
    )
}

fun FolderApiModel.map(): Folder {
    return when(this) {
        is CatalogApiModel -> map()
        is CategoryApiModel -> map(template.map())
    }
}