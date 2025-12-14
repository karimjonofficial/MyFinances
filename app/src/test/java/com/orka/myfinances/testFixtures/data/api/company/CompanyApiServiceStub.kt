package com.orka.myfinances.testFixtures.data.api.company

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyModel
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.testFixtures.resources.models.company

class CompanyApiServiceStub : CompanyApiService {
    override suspend fun get(credential: Credential): CompanyModel {
        return company.toModel()
    }
}