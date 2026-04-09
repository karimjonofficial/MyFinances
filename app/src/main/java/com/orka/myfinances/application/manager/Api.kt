package com.orka.myfinances.application.manager

import com.orka.myfinances.application.models.CompanyApiModel
import com.orka.myfinances.data.api.office.OfficeApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpStatusCode

suspend fun getCompany(client: HttpClient, access: String): ApiResponse {
    val response = client.get(
        urlString = "users/me/company/",
        block = {
            headers {
                append("Authorization", "Bearer $access")
            }
        }
    )
    return when(response.status) {
        HttpStatusCode.OK -> ApiResponse.Success(response.body<CompanyApiModel>())
        HttpStatusCode.Unauthorized -> ApiResponse.Unauthorized
        else -> ApiResponse.Failure
    }
}

suspend fun getOffice(
    client: HttpClient,
    officeId: Int,
    access: String
): OfficeApiModel? {
    val response = client.get(
        urlString = "branches/$officeId/",
        block = {
            headers {
                append("Authorization", "Bearer $access")
            }
        }
    )

    return if(response.status == HttpStatusCode.OK) {
        response.body()
    } else null
}