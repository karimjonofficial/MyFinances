package com.orka.myfinances.ui.screens.settings.defaults.client

import com.orka.myfinances.data.models.Id

interface SelectDefaultClientInteractor {
    fun select(id: Id)
}
