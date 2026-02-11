package com.orka.myfinances.testFixtures.data.api.company

import com.orka.myfinances.data.api.CompanyApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyModel

class EmptyCompanyApiStub : CompanyApi {
    override suspend fun get(credential: Credential): CompanyModel? {
        return null
    }
}