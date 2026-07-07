package com.orka.myfinances.lib.data.api.scoped.branch

import com.orka.myfinances.data.models.Id
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

suspend inline fun <reified T, reified Request, reified Response> BranchScopedApi<Response>.add(
    request: T,
    map: T.(Id) -> Request
): Response? {
    val apiRequest = request.map(branchId)
    val response = httpClient.post(
        urlString = baseUrl,
        block = { setBody(apiRequest) }
    )
    return response.body()
}