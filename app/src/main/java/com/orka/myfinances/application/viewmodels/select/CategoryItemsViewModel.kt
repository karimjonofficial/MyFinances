package com.orka.myfinances.application.viewmodels.select

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.repositories.folder.GetCategories
import com.orka.myfinances.lib.viewmodel.sourceful.list.map.SearchableMapListViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.item.CategoryItemModel
import kotlinx.coroutines.flow.asStateFlow

class CategoryItemsViewModel(
    private val get: GetCategories,
    logger: Logger
) : SearchableMapListViewModel<CategoryDto, CategoryItemModel>(
    get = { get.getCategories() },
    map = { it.toItemModel() },
    groupBy = { it.name.take(1).uppercase() },
    match = { query, item -> item.title.contains(query, ignoreCase = true) },
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }
}
