package com.orka.myfinances.ui.models.content

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.ui.FolderUiModel

data class FoldersContentModel(
    val folders: List<FolderUiModel>,
    val isDefaultCategorySet: Boolean,
    val pinnedCategories: List<Id>?
)