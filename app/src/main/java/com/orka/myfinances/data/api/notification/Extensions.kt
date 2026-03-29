package com.orka.myfinances.data.api.notification

import com.orka.myfinances.data.models.Id
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode

suspend fun NotificationApi.read(id: Id): Boolean {
    val response = httpClient.post("$baseUrl/${id.value}/read/")
    return response.status == HttpStatusCode.OK
}