package com.orka.myfinances.application.viewmodels.office

import com.orka.myfinances.data.dtos.office.OfficeDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.office.OfficeRepository
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapListViewModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.host.SelectOfficeScreenInteractor
import com.orka.myfinances.ui.screens.host.components.OfficeUiModel
import kotlinx.coroutines.flow.asStateFlow

class SelectOfficeScreenViewModel(
    private val repository: OfficeRepository,
    private val sessionManager: SessionManager,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapListViewModel<OfficeDto, OfficeUiModel>(
    loading = loading,
    failure = failure,
    get = { search -> repository.getAll(search) },
    map = { offices ->
        offices.sortedBy { it.name }
            .groupBy { it.name.stickyHeaderKey() }
            .mapValues { (_, list) -> list.map { it.toUiModel() } }
    },
    logger = logger
), SelectOfficeScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun select(id: Id) {
        launch {
            val office: OfficeDto? = repository.getById(id)
            if (office != null) {
                sessionManager.setOffice(Id(office.id))
            }
        }
    }
}
