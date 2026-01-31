package com.orka.myfinances.testFixtures.factories.api

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.testFixtures.data.api.company.DummyCompanyApiService
import com.orka.myfinances.testFixtures.data.api.companyOffice.DummyOfficeApi
import com.orka.myfinances.testFixtures.data.api.credential.DummyCredentialApiService
import com.orka.myfinances.testFixtures.data.api.user.DummyUserApiService

class ConfigurableApiProvider : ApiProvider {
    private var userApiService: UserApiService? = null
    private var companyApiService: CompanyApiService? = null
    private var officeApi: OfficeApi? = null
    private var credentialApiService: CredentialApiService? = null

    fun setUserApiService(userApiService: UserApiService) {
        this.userApiService = userApiService
    }

    fun setCompanyApiService(companyApiService: CompanyApiService) {
        this.companyApiService = companyApiService
    }

    fun setCompanyOfficeApiService(officeApi: OfficeApi) {
        this.officeApi = officeApi
    }

    override fun getUserApiService(): UserApiService {
        return userApiService ?: DummyUserApiService()
    }

    override fun getCompanyApiService(): CompanyApiService {
        return companyApiService ?: DummyCompanyApiService()
    }

    override fun officeApi(): OfficeApi {
        return officeApi ?: DummyOfficeApi()
    }

    override fun getCredentialApiService(): CredentialApiService {
        return credentialApiService ?: DummyCredentialApiService()
    }
}