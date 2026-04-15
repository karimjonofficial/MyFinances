package com.orka.myfinances.data.api.client

import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.company.CompanyScopedApi
import io.ktor.client.HttpClient

class ClientApi(
    override val httpClient: HttpClient,
    override val companyId: Id,
    override val baseUrl: String = "clients/"
) : CompanyScopedApi<ClientApiModel>
