package com.orka.myfinances.data.api.notification

import com.orka.myfinances.data.models.Notification
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode

class NotificationApi(private val client: HttpClient) {
    suspend fun getAll(): List<Notification>? {
        val response = client.get("notifications/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun read(id: Int): Boolean {
        val response = client.post("notifications/$id/read/")
        return response.status == HttpStatusCode.OK
    }
}
