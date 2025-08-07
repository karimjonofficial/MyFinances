package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyOfficeModel

class EmptyCompanyOfficeApiServiceStub : CompanyOfficeApiService {
    override suspend fun get(credential: Credential): CompanyOfficeModel? {
        return null
    }
}
