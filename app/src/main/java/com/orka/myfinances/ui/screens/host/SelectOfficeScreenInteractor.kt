package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface SelectOfficeScreenInteractor : StateFul {
    fun select(id: Id)
}