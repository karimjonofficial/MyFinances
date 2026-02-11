package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.CompanyApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyModel
import com.orka.myfinances.fixtures.resources.models.company1
import com.orka.myfinances.lib.extensions.models.toModel

class CompanyApiImpl : CompanyApi {
    override suspend fun get(credential: Credential): CompanyModel {
        return company1.toModel()
    }
}
