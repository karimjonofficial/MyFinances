package com.orka.myfinances.data.dtos.folder

import com.orka.myfinances.data.dtos.template.TemplateDto

data class CategoryDto(
    override val id: Int,
    override val name: String,
    val template: TemplateDto,
) : FolderDto
