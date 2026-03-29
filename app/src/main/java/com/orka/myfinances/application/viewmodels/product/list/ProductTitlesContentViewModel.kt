package com.orka.myfinances.application.viewmodels.product.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.data.api.title.ProductTitleApi1
import com.orka.myfinances.data.api.title.getByCategory
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.product.list.ProductTitleUiModel
import com.orka.myfinances.ui.screens.product.list.ProductTitlesContentInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductTitlesContentViewModel(
    private val categoryId: Id,
    private val productTitleApi: ProductTitleApi1,
    productTitleEvents: Flow<ProductTitleEvent>,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<ProductTitleApiModel, ProductTitleUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> productTitleApi.getByCategory(size, page, categoryId) },
    map = { chunk ->
        val content = chunk.results
            .sortedBy(ProductTitleApiModel::name)
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { (_, titles) -> titles.map(ProductTitleApiModel::toUiModel) }

        ChunkMapState(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = content
        )
    },
    logger = logger
), ProductTitlesContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()

        productTitleEvents.onEach {
            if (it.categoryId == categoryId) initialize()
        }.launchIn(viewModelScope)
    }

    override fun selectProduct(id: Id) {
        launch { navigator.navigateToProductTitle(id) }
    }
}