package com.orka.myfinances.data.dtos.folder

data class CatalogDto(
    override val id: Int,
    override val name: String,
) : FolderDto
