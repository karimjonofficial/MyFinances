package com.orka.myfinances.application.viewmodels.settings

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.settings.SettingsScreenInteractor
import com.orka.myfinances.ui.screens.settings.SettingsScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsScreenViewModel(
    private val defaultsRepository: GetDefaultCategory,
    flow: Flow<DefaultsEvent>,
    private val get: GetById<FolderDto>,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<SettingsScreenModel>(
    produceModel = {
        val id = defaultsRepository.getDefaultCategoryId()
        if(id != null) {
            val dto = get.getById(id)
            if (dto != null)
                SettingsScreenModel(dto.name)
            else null
        } else SettingsScreenModel()
    },
    loading = loading,
    failure = failure,
    logger = logger
), SettingsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        flow.onEach {
            if(it is DefaultsEvent.Category)
                initialize()
        }.launchIn(viewModelScope)
    }

    override fun toSelectDefaultCategory() {
        launch {
            navigator.navigateToSelectDefaultCategory()
        }
    }
}