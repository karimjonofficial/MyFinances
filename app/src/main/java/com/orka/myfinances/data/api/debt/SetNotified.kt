package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.api.debt.models.request.SetNotifiedApiRequest
import com.orka.myfinances.data.models.Id
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend fun DebtApi.setNotified(id: Id, notified: Boolean): Boolean {
    val response = httpClient.patch(
        urlString = "$baseUrl${id.value}/",
        block = { setBody(SetNotifiedApiRequest(notified)) }
    )

    return response.status == HttpStatusCode.OK
}