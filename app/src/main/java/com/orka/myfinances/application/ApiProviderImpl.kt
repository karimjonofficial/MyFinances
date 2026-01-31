package com.orka.myfinances.application

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.fixtures.data.api.CompanyApiServiceImpl
import com.orka.myfinances.fixtures.data.api.OfficeApiImpl
import com.orka.myfinances.fixtures.data.api.CredentialApiServiceImpl
import com.orka.myfinances.fixtures.data.api.UserApiServiceImpl

class ApiProviderImpl : ApiProvider {
    override fun getUserApiService(): UserApiService {
        return UserApiServiceImpl()
    }

    override fun getCompanyApiService(): CompanyApiService {
        return CompanyApiServiceImpl()
    }

    override fun officeApi(): OfficeApi {
        return OfficeApiImpl()
    }

    override fun getCredentialApiService(): CredentialApiService {
        return CredentialApiServiceImpl()
    }
}