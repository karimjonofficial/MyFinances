package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.components.ClientCardModel
import com.orka.myfinances.ui.components.UserCardModel

data class DebtScreenModel(
    val price: String,
    val startDate: String,
    val endDateTime: String?,
    val notified: Boolean,
    val client: ClientCardModel,
    val isOverdue: Boolean,
    val id: Id,
    val user: UserCardModel,
    val clientId: Id,
    val description: String? = null
)