package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.models.Id
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend fun DebtApi.setPaid(id: Id): Boolean {
    val response = httpClient.patch(
        urlString = "$baseUrl${id.value}/",
        block = { setBody(SetPaidApiRequest(true)) }
    )

    return response.status == HttpStatusCode.OK
}