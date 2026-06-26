package com.orka.myfinances.application.viewmodels.template.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.list.TemplateUiModel
import com.orka.myfinances.ui.screens.templates.list.TemplatesScreenInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TemplatesScreenViewModel(
    private val repository: TemplateRepository,
    events: Flow<TemplateEvent>,
    private val navigator: Navigator,
    formatDecimal: FormatDecimal,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<TemplateDto, TemplateUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page, query -> repository.getChunk(size, page, query) },
    map = { chunk ->
        val map = chunk.results
            .sortedBy { it.name }
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { it.value.map { template -> template.toUiModel(formatDecimal) } }

        ChunkUiModel(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), TemplatesScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach { initialize() }.launchIn(viewModelScope)
    }

    override fun addTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }

    override fun select(template: TemplateUiModel) {
        launch {
            navigator.navigateToTemplate(template.id)
        }
    }
}