package com.orka.myfinances.application.viewmodels.product.sheet

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.GetProductTitlesByCategory
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.item.ProductTitleItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductTitleBottomSheetViewModel(
    private val categoryId: Id,
    private val getByCategory: GetProductTitlesByCategory,
    flow: Flow<ProductTitleEvent>,
    logger: Logger
) : SearchableMapChunkViewModel<ProductTitleDto, ProductTitleItemModel>(
    get = { size, page -> getByCategory.getByCategory(size, page, categoryId, null) },
    searchRepository = { size, page, q -> getByCategory.getByCategory(size, page, categoryId, q) },
    map = { chunk ->
        val content = chunk.results
            .sortedBy(ProductTitleDto::name)
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { (_, titles) -> titles.map(ProductTitleDto::toItemModel) }

        ChunkUiModel(
            size = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = content
        )
    },
    logger = logger
), ProductTitleBottomSheetInteractor {
    val uiState = state.asStateFlow()

    init {
        flow.onEach { refresh() }.launchIn(viewModelScope)
    }
}
