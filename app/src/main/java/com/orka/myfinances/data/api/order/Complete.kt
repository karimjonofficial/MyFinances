package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.models.Id
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend fun OrderApi.complete(id: Id): Boolean {
    val response = httpClient.patch(
        urlString = "$baseUrl${id.value}/",
        block = { setBody(id.toCompleteOrderApiRequest()) }
    )

    return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
}