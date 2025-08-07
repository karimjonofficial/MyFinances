package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyModel
import com.orka.myfinances.lib.extensions.toModel
import com.orka.myfinances.testLib.company

class EmptyCompanyApiServiceStub : CompanyApiService {
    override suspend fun get(credential: Credential): CompanyModel? {
        return company.toModel()
    }

}
