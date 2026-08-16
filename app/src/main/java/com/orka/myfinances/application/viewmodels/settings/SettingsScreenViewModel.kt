package com.orka.myfinances.application.viewmodels.settings

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.DefaultsRepository
import com.orka.myfinances.data.repositories.printer.PrinterRepository
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.refreshable.RefreshableBaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.printer.PrinterStatus
import com.orka.myfinances.ui.models.screen.SettingsScreenModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.settings.main.SettingsScreenInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsScreenViewModel(
    private val defaultsRepository: DefaultsRepository,
    private val printerRepository: PrinterRepository,
    defaultsFlow: Flow<DefaultsEvent>,
    private val printerStatus: StateFlow<PrinterStatus>,
    private val get: GetById<FolderDto>,
    private val getTemplate: GetById<TemplateDto>,
    private val navigator: Navigator,
    logger: Logger
) : RefreshableBaseViewModel<SettingsScreenModel>(
    produceInitialState = {
        val defaultCategoryId = defaultsRepository.getDefaultCategoryId()
        val defaultCategory = if (defaultCategoryId != null) get.getById(defaultCategoryId)?.name else null

        val defaultTemplateId = defaultsRepository.getDefaultTemplateId()
        val defaultTemplate = if (defaultTemplateId != null) getTemplate.getById(defaultTemplateId)?.name else null

        val status = printerStatus.value
        val printer = if (status is PrinterStatus.Connected) status.printer.name else null
        val defaultPrinterId = defaultsRepository.getDefaultPrinter()
        val defaultPrinter = if (defaultPrinterId != null) printerRepository.getById(defaultPrinterId)?.model?.name else null

        State.Success(
            value = SettingsScreenModel(
                defaultCategory = defaultCategory,
                defaultTemplate = defaultTemplate,
                defaultPrinter = defaultPrinter,
                pairedPrinter = printer
            )
        )
    },
    logger = logger
), SettingsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        defaultsFlow.onEach {
            when (it) {
                DefaultsEvent.Printer, DefaultsEvent.Category, DefaultsEvent.Template -> initialize()
            }
        }.launchIn(viewModelScope)
        printerStatus.onEach { initialize() }.launchIn(viewModelScope)
    }

    override fun toSelectDefaultCategory() {
        launch {
            navigator.navigateToSelectDefaultCategory()
        }
    }

    override fun toSelectDefaultTemplate() {
        launch {
            navigator.navigateToSelectDefaultTemplate()
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

    override fun toDefaultPrinter() {
        launch {
            navigator.navigateToDefaultPrinter()
        }
    }
}
