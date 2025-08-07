package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyOfficeModel
import com.orka.myfinances.lib.extensions.toModel
import com.orka.myfinances.testLib.companyOffice

class CompanyOfficeApiServiceStub : CompanyOfficeApiService {
    override suspend fun get(credential: Credential): CompanyOfficeModel? {
        return companyOffice.toModel()
    }
}
