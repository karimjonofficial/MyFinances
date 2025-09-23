package com.orka.myfinances.factories

import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel

class ViewModelProviderImpl(
    private val addTemplateScreenViewModel: AddTemplateScreenViewModel,
    private val templatesScreenViewModel: TemplatesScreenViewModel,
    private val homeScreenViewModel: HomeScreenViewModel
) : ViewModelProvider {
    override fun addTemplateViewModel(): AddTemplateScreenViewModel {
        return addTemplateScreenViewModel
    }

    override fun templatesViewModel(): TemplatesScreenViewModel {
        templatesScreenViewModel.initialize()
        return templatesScreenViewModel
    }

    override fun homeViewModel(): HomeScreenViewModel {
        return homeScreenViewModel
    }
}

