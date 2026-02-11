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

class ConfigurableApiProvider : ApiProvider {
    private var userApi: UserApi? = null
    private var companyApi: CompanyApi? = null
    private var officeApi: OfficeApi? = null
    private var credentialApi: CredentialApi? = null

    fun setUserApiService(userApi: UserApi) {
        this.userApi = userApi
    }

    fun setCompanyApiService(companyApi: CompanyApi) {
        this.companyApi = companyApi
    }

    fun setCompanyOfficeApiService(officeApi: OfficeApi) {
        this.officeApi = officeApi
    }

    override fun getUserApiService(): UserApi {
        return userApi ?: DummyUserApi()
    }

    override fun getCompanyApiService(): CompanyApi {
        return companyApi ?: DummyCompanyApi()
    }

    override fun officeApi(): OfficeApi {
        return officeApi ?: DummyOfficeApi()
    }

    override fun getCredentialApiService(): CredentialApi {
        return credentialApi ?: DummyCredentialApi()
    }
}