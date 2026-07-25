package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.api.folder.models.request.AddFolderApiRequest
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
    return if (isCatalog) {
        CatalogDto(
            id = id,
            name = name,
        )
    } else {
        if(template != null) {
            CategoryDto(
                id = id,
                name = name,
                template = template.toDto(),
            )
        } else throw Exception("Template is null in a category. ID: $id")
    }
}

fun AddFolderRequest.toApiRequest(branchId: Id): AddFolderApiRequest {
    return AddFolderApiRequest(
        name = name,
        type = type,
        branchId = branchId.value,
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
        fields = fields?.map { it.toEntity() }
    )
}
