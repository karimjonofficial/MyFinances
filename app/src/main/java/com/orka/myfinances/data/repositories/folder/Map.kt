package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.api.folder.models.request.AddFolderApiRequest
import com.orka.myfinances.data.api.folder.models.response.CatalogApiModel
import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.data.dtos.folder.CatalogDto
import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.dtos.template.TemplateFieldDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.template.toDto

fun FolderApiModel.toDto(): FolderDto {
    return when (this) {
        is CatalogApiModel -> toDto()
        is CategoryApiModel -> toDto()
    }
}

fun CatalogApiModel.toDto(): CatalogDto {
    return CatalogDto(
        id = id,
        name = name,
    )
}

fun CategoryApiModel.toDto(): CategoryDto {
    return CategoryDto(
        id = id,
        name = name,
        template = template.toDto(),
    )
}

fun AddFolderRequest.map(officeId: Id) = AddFolderApiRequest(
    name = name,
    parentId = parentId?.value,
    officeId = officeId.value,
    type = type,
    templateId = templateId?.value,
)

fun AddFolderRequest.toApiRequest(officeId: Id): AddFolderApiRequest {
    return AddFolderApiRequest(
        name = name,
        type = type,
        officeId = officeId.value,
        templateId = templateId?.value,
        parentId = parentId?.value
    )
}

fun TemplateFieldDto.toEntity(): TemplateField {
    return TemplateField(
        id = Id(id),
        name = name,
        type = type
    )
}

fun TemplateDto.toEntity(): Template {
    return Template(
        id = Id(id),
        name = name,
        fields = fields.map { it.toEntity() }
    )
}