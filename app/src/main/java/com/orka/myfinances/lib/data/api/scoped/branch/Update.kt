package com.orka.myfinances.lib.data.api.scoped.branch

import com.orka.myfinances.data.models.Id
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T, reified R> BranchScopedApi<*>.update(
    id: Id,
    request: T,
    map: T.(Id) -> R
): Boolean {
    val apiRequest = request.map(branchId)
    val response = httpClient.put(
        urlString = "$baseUrl${id.value}/",
        block = { setBody(apiRequest) }
    )

    return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
}