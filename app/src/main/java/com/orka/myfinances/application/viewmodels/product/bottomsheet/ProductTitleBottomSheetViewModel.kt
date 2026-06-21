package com.orka.myfinances.application.viewmodels.product.bottomsheet

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.getByCategory
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.models.ProductTitleItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductTitleBottomSheetViewModel(
    private val categoryId: Id,
    private val productTitleApi: ProductTitleApi,
    flow: Flow<ProductTitleEvent>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<ProductTitleApiModel, ProductTitleItemModel>(
    loading = loading,
    failure = failure,
    get = { size, page, query -> productTitleApi.getByCategory(size, page, categoryId, query) },
    map = { chunk ->
        val content = chunk.results
            .sortedBy(ProductTitleApiModel::name)
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { (_, titles) -> titles.map(ProductTitleApiModel::toItemModel) }

        ChunkUiModel(
            count = chunk.count,
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
