package com.orka.myfinances.testFixtures.data.api.companyOffice

import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyOfficeModel
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.testFixtures.resources.models.office

class CompanyOfficeApiServiceStub : CompanyOfficeApiService {
    override suspend fun get(credential: Credential): CompanyOfficeModel {
        return office.toModel()
    }
}
