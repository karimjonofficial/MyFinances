package com.orka.myfinances.testFixtures.resources.dtos

import com.orka.myfinances.data.dtos.notification.NotificationDto
import com.orka.myfinances.testFixtures.resources.dateTime

val notificationDto1 = NotificationDto(
    id = 1,
    title = "Title 1",
    message = "Message 1",
    read = false,
    dateTime = dateTime
)

val notificationDtos = listOf(notificationDto1)
