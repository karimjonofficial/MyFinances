package com.orka.myfinances.application.viewmodels.defaults.category

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.item.DefaultCategoryItemModel

fun toItemModels(folders: List<FolderDto>): Map<String, List<DefaultCategoryItemModel>> {
    return folders.filterIsInstance<CategoryDto>()
        .map { DefaultCategoryItemModel(id = Id(it.id), title = it.name) }
        .groupBy { it.title.take(1).uppercase() }
}
