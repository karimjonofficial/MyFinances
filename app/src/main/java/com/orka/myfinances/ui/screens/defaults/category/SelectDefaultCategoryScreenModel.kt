package com.orka.myfinances.ui.screens.defaults.category

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.item.DefaultCategoryItemModel

data class SelectDefaultCategoryScreenModel(
    val map: Map<String, List<DefaultCategoryItemModel>>,
    val defaultId: Id?
)
