package com.orka.myfinances.data.api.sale

import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.branch.BranchScopedApi
import io.ktor.client.HttpClient

class SaleApi(
    override val branchId: Id,
    override val httpClient: HttpClient,
    override val baseUrl: String = "sales/"
) : BranchScopedApi<SaleApiModel>