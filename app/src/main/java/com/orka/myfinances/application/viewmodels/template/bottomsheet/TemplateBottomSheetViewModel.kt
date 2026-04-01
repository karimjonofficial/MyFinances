package com.orka.myfinances.application.viewmodels.template.bottomsheet

import com.orka.myfinances.application.viewmodels.folder.home.toItemModel
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.data.api.template.TemplateApi1
import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.lib.data.api.scoped.office.getChunk
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.screens.folder.models.TemplateItemModel
import kotlinx.coroutines.flow.asStateFlow

class TemplateBottomSheetViewModel(
    private val templateApi: TemplateApi1,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<TemplateApiModel, TemplateItemModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> templateApi.getChunk(size, page) },
    map = { chunk ->
        val map = chunk.results
            .sortedBy { it.name }
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { it.value.map { item -> item.toItemModel() } }

        ChunkMapState(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), TemplateBottomSheetInteractor {
    val uiState = state.asStateFlow()
}
