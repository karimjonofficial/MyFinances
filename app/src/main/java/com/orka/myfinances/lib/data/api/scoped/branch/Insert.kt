package com.orka.myfinances.lib.data.api.scoped.branch

import com.orka.myfinances.data.models.Id
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T, reified R> BranchScopedApi<*>.insert(
    request: T,
    map: T.(Id) -> R
): Boolean {
    val apiRequest = request.map(branchId)
    val response = httpClient.post(
        urlString = baseUrl,
        block = { setBody(apiRequest) }
    )
    val created = response.status == HttpStatusCode.Created
    return created
}

suspend inline fun <reified T> BranchScopedApi<*>.insert(request: T): Boolean {
    val response = httpClient.post(
        urlString = baseUrl,
        block = { setBody(request) }
    )
    val created = response.status == HttpStatusCode.Created
    return created
}