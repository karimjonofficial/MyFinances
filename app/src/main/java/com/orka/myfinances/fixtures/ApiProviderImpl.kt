package com.orka.myfinances.fixtures

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.factories.ApiProvider

class ApiProviderImpl : ApiProvider {
    override fun getUserApiService(): UserApiService {
        TODO("Not yet implemented")
    }

    override fun getCompanyApiService(): CompanyApiService {
        TODO("Not yet implemented")
    }

    override fun getCompanyOfficeApiService(): CompanyOfficeApiService {
        TODO("Not yet implemented")
    }

    override fun getCredentialApiService(): CredentialApiService {
        TODO("Not yet implemented")
    }

}