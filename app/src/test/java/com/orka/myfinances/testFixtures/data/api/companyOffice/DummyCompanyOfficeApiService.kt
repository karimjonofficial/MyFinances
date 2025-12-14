package com.orka.myfinances.testFixtures.data.api.companyOffice

import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyOfficeModel

class DummyCompanyOfficeApiService : CompanyOfficeApiService {
    override suspend fun get(credential: Credential): CompanyOfficeModel? {
        return null
    }
}