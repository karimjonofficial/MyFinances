package com.orka.myfinances.fixtures.resources.models

import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.fixtures.resources.dateTime

val notification1 = Notification(
    id = id1,
    title = "Order",
    message = "Your order has been shipped.",
    dateTime = dateTime,
    read = false
)
val notification2 = Notification(
    id = id2,
    title = "Payment",
    message = "Your payment was successful.",
    dateTime = dateTime,
    read = true
)
val notifications = listOf(notification1, notification2)
