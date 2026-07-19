package com.orka.myfinances.ui.screens.folder.home.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

data class FoldersContentModel(
    val folders: List<FolderUiModel>,
    val isDefaultCategorySet: Boolean,
    val pinnedCategories: List<Id>?
)
