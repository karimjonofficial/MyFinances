package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class ReceiveApi(
    override val officeId: Id,
    override val httpClient: HttpClient,
    override val baseUrl: String = "receives/",
) : OfficeScopedApi