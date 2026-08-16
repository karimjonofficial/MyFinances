package com.orka.myfinances.application.viewmodels.select

import com.orka.myfinances.application.viewmodels.folder.home.toItemModel
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.item.TemplateItemModel
import kotlinx.coroutines.flow.asStateFlow

class TemplateItemsViewModel(
    getChunk: GetChunk<TemplateDto>,
    searchChunk: SearchChunk<TemplateDto>,
    logger: Logger
) : SearchableMapChunkViewModel<TemplateDto, TemplateItemModel>(
    get = getChunk,
    search = searchChunk,
    map = { it.toItemModel() },
    groupBy = { it.name.take(1).uppercase() },
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }
}
