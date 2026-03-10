package com.orka.myfinances.data.api.user

import com.orka.myfinances.application.models.CompanyApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class UserApi(private val client: HttpClient) {
    suspend fun getMe(): UserApiModel? {
        val response = client.get("users/me/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getCompany(): CompanyApiModel? {
        val response = client.get("users/me/company/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }
}
