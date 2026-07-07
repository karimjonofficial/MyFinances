package com.orka.myfinances.application.manager

import com.orka.myfinances.application.models.CompanyApiModel
import com.orka.myfinances.data.api.branch.BranchApiModel
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

suspend fun getBranch(
    client: HttpClient,
    branchId: Int,
    access: String
): BranchApiModel? {
    val response = client.get(
        urlString = "branches/$branchId/",
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