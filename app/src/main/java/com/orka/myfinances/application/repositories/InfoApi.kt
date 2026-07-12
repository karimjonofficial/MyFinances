package com.orka.myfinances.application.repositories

import com.orka.myfinances.data.api.company.CompanyApiModel
import com.orka.myfinances.data.models.Id
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

class InfoApi(private val httpClient: HttpClient) {
    suspend fun getCompanyId(access: String): Id {
        val company =  httpClient.get(
            urlString = "users/me/company/",
            block = {
                header("Authorization", "Bearer $access")
            }
        ).body<CompanyApiModel>()

        return Id(company.id)
    }
}