package com.orka.myfinances.application.viewmodels.settings

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.refreshable.RefreshableBaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.printer.PrinterStatus
import com.orka.myfinances.ui.models.screen.SettingsScreenModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.settings.main.SettingsScreenInteractor
import com.orka.myfinances.ui.statuses.failure.failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsScreenViewModel(
    private val defaultsRepository: GetDefaultCategory,
    defaultsFlow: Flow<DefaultsEvent>,
    private val printerStatus: StateFlow<PrinterStatus>,
    private val get: GetById<FolderDto>,
    private val navigator: Navigator,
    logger: Logger
) : RefreshableBaseViewModel<SettingsScreenModel>(
    produceInitialState = {
        val id = defaultsRepository.getDefaultCategoryId()
        val status = printerStatus.value
        val printer = if(status is PrinterStatus.Connected) status.printer.name else null
        if(id != null) {
            val dto = get.getById(id)
            if (dto != null)
                State.Success(SettingsScreenModel(
                    defaultCategory = dto.name,
                    pairedPrinter = printer
                ))
            else State.Failure(failure)
        } else State.Success(SettingsScreenModel(pairedPrinter = printer))
    },
    logger = logger
), SettingsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        defaultsFlow.onEach {
            if(it is DefaultsEvent.Category)
                initialize()
        }.launchIn(viewModelScope)
        printerStatus.onEach { initialize() }.launchIn(viewModelScope)
    }

    override fun toSelectDefaultCategory() {
        launch {
            navigator.navigateToSelectDefaultCategory()
        }
    }

    override fun toPinnedCategories() {
        launch {
            navigator.navigateToPinnedCategories()
        }
    }

    override fun toPrinters() {
        launch {
            navigator.navigateToPrinters()
        }
    }
}
