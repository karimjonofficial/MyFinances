package com.orka.myfinances.application.viewmodels.product.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.GetProductTitlesByCategory
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.models.ui.ProductTitleUiModel
import com.orka.myfinances.ui.screens.product.list.ProductTitlesContentInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductTitlesContentViewModel(
    private val categoryId: Id,
    private val getByCategory: GetProductTitlesByCategory,
    productTitleEvents: Flow<ProductTitleEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SearchableMapChunkViewModel<ProductTitleDto, ProductTitleUiModel>(
    get = { size, page -> getByCategory.getByCategory(size, page, categoryId, null) },
    search = { size, page, q -> getByCategory.getByCategory(size, page, categoryId, q) },
    map = { chunk ->
        val content = chunk.results
            .sortedBy(ProductTitleDto::name)
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { (_, titles) -> titles.map(ProductTitleDto::toUiModel) }

        ChunkUiModel(
            size = chunk.count,
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

        productTitleEvents.onEach { event ->
            val s = state.value
            if (s is State.Success) {
                if (event.titleId == null) refresh()
                else {
                    val c = s.value.content.values
                    c.forEach { collection ->
                        if (collection.any { it.id == event.titleId }) refresh()
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun selectProduct(id: Id) {
        launch { navigator.navigateToProductTitle(id) }
    }
}