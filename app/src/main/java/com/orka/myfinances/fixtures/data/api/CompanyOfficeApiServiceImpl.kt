package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyOfficeModel
import com.orka.myfinances.fixtures.resources.models.companyOffice1
import com.orka.myfinances.lib.extensions.models.toModel

class CompanyOfficeApiServiceImpl : CompanyOfficeApiService {
    override suspend fun get(credential: Credential): CompanyOfficeModel? {
        return companyOffice1.toModel()
    }
}
