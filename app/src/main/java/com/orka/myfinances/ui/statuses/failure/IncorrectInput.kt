package com.orka.myfinances.ui.statuses.failure

import com.orka.myfinances.lib.ui.state.FailureStatus

data class IncorrectInput(val fields: List<String>) : FailureStatus
