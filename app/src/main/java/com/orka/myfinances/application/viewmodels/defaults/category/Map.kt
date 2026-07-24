package com.orka.myfinances.application.viewmodels.defaults.category

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.item.CategoryBottomSheetItemModel

fun toItemModels(folders: List<FolderDto>): Map<String, List<CategoryBottomSheetItemModel>> {
    return folders.filterIsInstance<CategoryDto>()
        .map { CategoryBottomSheetItemModel(id = Id(it.id), title = it.name) }
        .groupBy { it.title.take(1).uppercase() }
}
