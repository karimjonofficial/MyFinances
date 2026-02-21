package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.ui.components.ClientCardModel

data class DebtScreenModel(
    val price: String,
    val startDate: String,
    val endDateTime: String?,
    val notified: Boolean,
    val client: ClientCardModel,
    val isOverdue: Boolean,
    val debt: Debt,
    val description: String? = null
)