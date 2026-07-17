package com.orka.myfinances.application.viewmodels.notification

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.notification.NotificationDto
import com.orka.myfinances.data.repositories.notification.ReadNotification
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.format.FormatLocalDate
import com.orka.myfinances.format.FormatTime
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.notificationDto1
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NotificationsScreenViewModelTest : MainDispatcherContext() {
    private val getChunk = mockk<GetChunk<NotificationDto>>()
    private val readNotification = mockk<ReadNotification>()
    private val formatLocalDate = mockk<FormatLocalDate>()
    private val formatTime = mockk<FormatTime>()
    private val logger = mockk<Logger>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

    @Test
    fun `initialize success`() = runTest {
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(notificationDto1)
        )
        coEvery { getChunk.getChunk(any(), any(), any()) } returns chunk
        every { formatLocalDate.formatLocalDate(any()) } returns "2024-01-01"
        every { formatTime.formatTime(any()) } returns "12:00"

        val viewModel = NotificationsScreenViewModel(
            getChunk, readNotification, formatLocalDate, formatTime, logger, loading, failure
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
