package com.orka.myfinances.application.viewmodels.select

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.repositories.folder.GetCategories
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapListViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.screens.settings.home.CategoryItemModel
import kotlinx.coroutines.flow.asStateFlow

class CategoryItemsViewModel(
    private val get: GetCategories,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapListViewModel<CategoryDto, CategoryItemModel>(
    get = { get.getCategories(it) },
    map = { list ->
        list.map { it.toItemModel() }
            .groupBy { it.title.take(1).uppercase() }
    },
    loading = loading,
    failure = failure,
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }
}
