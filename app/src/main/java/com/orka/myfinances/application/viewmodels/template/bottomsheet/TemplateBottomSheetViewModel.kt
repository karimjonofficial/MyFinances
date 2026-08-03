package com.orka.myfinances.application.viewmodels.template.bottomsheet

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.folder.home.toItemModel
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.item.TemplateItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TemplateBottomSheetViewModel(
    getChunk: GetChunk<TemplateDto>,
    searchChunk: SearchChunk<TemplateDto>,
    flow: Flow<TemplateEvent>,
    logger: Logger
) : SearchableMapChunkViewModel<TemplateDto, TemplateItemModel>(
    get = getChunk,
    searchRepository = searchChunk,
    map = { chunk ->
        val map = chunk.results
            .sortedBy { it.name }
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { it.value.map { item -> item.toItemModel() } }

        ChunkUiModel(
            size = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), TemplateBottomSheetInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        flow.onEach { refresh() }.launchIn(viewModelScope)
    }
}
