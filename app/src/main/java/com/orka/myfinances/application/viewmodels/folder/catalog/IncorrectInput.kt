package com.orka.myfinances.application.viewmodels.folder.catalog

import com.orka.myfinances.lib.ui.state.FailureType

data class IncorrectInput(val fields: List<String>) : FailureType