package com.orka.myfinances.application.viewmodels.receive.bottomsheet

import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.getByCategory
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.screens.receive.add.ProductTitleItemModel
import kotlinx.coroutines.flow.asStateFlow

class ProductTitleBottomSheetViewModel(
    private val categoryId: Id,
    private val productTitleApi: ProductTitleApi,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<ProductTitleApiModel, ProductTitleItemModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> productTitleApi.getByCategory(size, page, categoryId) },
    map = { chunk ->
        val content = chunk.results
            .sortedBy(ProductTitleApiModel::name)
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { (_, titles) -> titles.map(ProductTitleApiModel::toItemModel) }

        ChunkMapState(
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
}
