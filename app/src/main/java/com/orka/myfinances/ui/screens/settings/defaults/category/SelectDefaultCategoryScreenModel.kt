package com.orka.myfinances.ui.screens.settings.defaults.category

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.item.CategoryBottomSheetItemModel

data class SelectDefaultCategoryScreenModel(
    val map: Map<String, List<CategoryBottomSheetItemModel>>,
    val defaultId: Id?
)
