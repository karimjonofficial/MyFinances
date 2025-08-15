package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyModel
import com.orka.myfinances.fixtures.company
import com.orka.myfinances.lib.extensions.toModel

class CompanyApiServiceImpl : CompanyApiService {
    override suspend fun get(credential: Credential): CompanyModel? {
        return company.toModel()
    }
}
