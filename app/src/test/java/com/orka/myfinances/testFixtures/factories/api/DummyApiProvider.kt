package com.orka.myfinances.testFixtures.factories.api

import com.orka.myfinances.data.api.CompanyApi
import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.api.CredentialApi
import com.orka.myfinances.data.api.UserApi
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.testFixtures.data.api.company.DummyCompanyApi
import com.orka.myfinances.testFixtures.data.api.office.DummyOfficeApi
import com.orka.myfinances.testFixtures.data.api.credential.DummyCredentialApi
import com.orka.myfinances.testFixtures.data.api.user.DummyUserApi

class DummyApiProvider : ApiProvider {
    override fun getUserApiService(): UserApi {
        return DummyUserApi()
    }

    override fun getCompanyApiService(): CompanyApi {
        return DummyCompanyApi()
    }

    override fun officeApi(): OfficeApi {
        return DummyOfficeApi()
    }

    override fun getCredentialApiService(): CredentialApi {
        return DummyCredentialApi()
    }
}