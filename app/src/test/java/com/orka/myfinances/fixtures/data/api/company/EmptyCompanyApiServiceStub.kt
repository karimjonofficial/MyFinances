package com.orka.myfinances.fixtures.data.api.company

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyModel

class EmptyCompanyApiServiceStub : CompanyApiService {
    override suspend fun get(credential: Credential): CompanyModel? {
        return null
    }
}