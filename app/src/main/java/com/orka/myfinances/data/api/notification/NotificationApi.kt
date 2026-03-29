package com.orka.myfinances.data.api.notification

import com.orka.myfinances.lib.data.api.Api
import io.ktor.client.HttpClient

class NotificationApi(
    override val httpClient: HttpClient,
    override val baseUrl: String = "notifications/"
) : Api