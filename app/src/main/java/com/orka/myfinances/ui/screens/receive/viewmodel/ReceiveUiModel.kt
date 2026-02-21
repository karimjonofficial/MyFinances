package com.orka.myfinances.ui.screens.receive.viewmodel

import com.orka.myfinances.data.models.receive.Receive

data class ReceiveUiModel(
    val price: String,
    val dateTime: String,
    val receive: Receive,
    val items: List<ReceiveItemModel>
)