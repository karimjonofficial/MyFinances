package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyOfficeModel

class DummyCompanyOfficeApiService : CompanyOfficeApiService {
    override suspend fun get(credential: Credential): CompanyOfficeModel? {
        return null
    }
}