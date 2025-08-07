package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyModel

class DummyCompanyApiService : CompanyApiService {
    override suspend fun get(credential: Credential): CompanyModel? {
        return null
    }
}