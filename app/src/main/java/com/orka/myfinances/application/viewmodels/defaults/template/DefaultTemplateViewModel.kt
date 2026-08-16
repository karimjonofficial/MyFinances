package com.orka.myfinances.application.viewmodels.defaults.template

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultTemplate
import com.orka.myfinances.data.repositories.defaults.SetDefaultTemplate
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.settings.defaults.template.SelectDefaultTemplateInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DefaultTemplateViewModel(
    private val getDefaultTemplate: GetDefaultTemplate,
    private val setDefaultTemplate: SetDefaultTemplate,
    flow: Flow<DefaultsEvent>,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleViewModel<Id?, Id?>(
    get = { getDefaultTemplate.getDefaultTemplateId() },
    map = { it },
    logger = logger
), SelectDefaultTemplateInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        flow.onEach {
            if (it is DefaultsEvent.Template)
                initialize()
        }.launchIn(viewModelScope)
    }

    override fun select(id: Id) {
        tryTransition { _ ->
            setDefaultTemplate.setDefaultTemplateId(id)
            navigator.back()
            State.Success(id)
        }
    }
}
