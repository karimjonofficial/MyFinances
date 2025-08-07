package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.fixtures.data.api.DummyCompanyApiService
import com.orka.myfinances.fixtures.data.api.DummyCompanyOfficeApiService
import com.orka.myfinances.fixtures.data.api.DummyUserApiService
import com.orka.myfinances.fixtures.data.api.credential.DummyCredentialApiService

class ConfigurableApiProvider : ApiProvider {
    private var userApiService: UserApiService? = null
    private var companyApiService: CompanyApiService? = null
    private var companyOfficeApiService: CompanyOfficeApiService? = null
    private var credentialApiService: CredentialApiService? = null

    fun setUserApiService(userApiService: UserApiService) {
        this.userApiService = userApiService
    }

    fun setCompanyApiService(companyApiService: CompanyApiService) {
        this.companyApiService = companyApiService
    }

    fun setCompanyOfficeApiService(companyOfficeApiService: CompanyOfficeApiService) {
        this.companyOfficeApiService = companyOfficeApiService
    }

    fun setCredentialApiService(credentialApiService: CredentialApiService) {
        this.credentialApiService = credentialApiService
    }

    override fun getUserApiService(): UserApiService {
        return userApiService ?: DummyUserApiService()
    }

    override fun getCompanyApiService(): CompanyApiService {
        return companyApiService ?: DummyCompanyApiService()
    }

    override fun getCompanyOfficeApiService(): CompanyOfficeApiService {
        return companyOfficeApiService ?: DummyCompanyOfficeApiService()
    }

    override fun getCredentialApiService(): CredentialApiService {
        return credentialApiService ?: DummyCredentialApiService()
    }
}
