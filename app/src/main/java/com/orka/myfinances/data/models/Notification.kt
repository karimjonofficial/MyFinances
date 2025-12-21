package com.orka.myfinances.data.models

data class Notification(
    val id: Id,
    val message: String,
    val read: Boolean
)