package com.orka.myfinances.ui.screens.notification

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Notification
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.ListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

typealias IListViewModel = com.orka.myfinances.lib.ui.viewmodel.ListViewModel<Notification>

class NotificationScreenViewModel(
    private val client: HttpClient,
    logger: Logger,
    loading: UiText,
    failure: UiText
) : ListViewModel<Notification>(
    loading = loading,
    failure = failure,
    repository = {
        try {
            val response = client.get("notifications/")
            if (response.status == HttpStatusCode.OK) {
                response.body<List<Notification>>()
            } else null
        } catch (_: Exception) {
            null
        }
    },
    logger = logger
), IListViewModel {
    override val uiState = state.asStateFlow()

    fun read(notification: Notification) = launch {
        if(!notification.read) {
            try {
                val response = client.post("notifications/${notification.id.value}/read/")
                if (response.status == HttpStatusCode.OK) {
                    initialize()
                }
            } catch (_: Exception) {
                // Handle error
            }
        }
    }
}
