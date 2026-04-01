package com.orka.myfinances.data.api.template

import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class TemplateApi(
    private val client: HttpClient,
    private val office: Office,
) {
    suspend fun getAll(): List<TemplateApiModel>? {
        val response = client.get("templates/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): TemplateApiModel? {
        val response = client.get("templates/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddTemplateRequest): Boolean {
        val response = client.post(
            urlString = "templates/",
            block = { setBody(request.map(office.id.value)) }
        )
        return response.status == HttpStatusCode.Created
    }
}