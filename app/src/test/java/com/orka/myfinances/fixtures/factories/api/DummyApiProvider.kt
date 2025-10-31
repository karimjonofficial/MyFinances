package com.orka.myfinances.fixtures.factories.api

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.fixtures.data.api.company.DummyCompanyApiService
import com.orka.myfinances.fixtures.data.api.companyOffice.DummyCompanyOfficeApiService
import com.orka.myfinances.fixtures.data.api.credential.DummyCredentialApiService
import com.orka.myfinances.fixtures.data.api.user.DummyUserApiService

class DummyApiProvider : ApiProvider {
    override fun getUserApiService(): UserApiService {
        return DummyUserApiService()
    }

    override fun getCompanyApiService(): CompanyApiService {
        return DummyCompanyApiService()
    }

    override fun getCompanyOfficeApiService(): CompanyOfficeApiService {
        return DummyCompanyOfficeApiService()
    }

    override fun getCredentialApiService(): CredentialApiService {
        return DummyCredentialApiService()
    }
}