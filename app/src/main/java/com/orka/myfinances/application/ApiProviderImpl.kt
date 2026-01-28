package com.orka.myfinances.application

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.api.UserApiService
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.fixtures.data.api.CompanyApiServiceImpl
import com.orka.myfinances.fixtures.data.api.CompanyOfficeApiServiceImpl
import com.orka.myfinances.fixtures.data.api.CredentialApiServiceImpl
import com.orka.myfinances.fixtures.data.api.UserApiServiceImpl

class ApiProviderImpl : ApiProvider {
    override fun getUserApiService(): UserApiService {
        return UserApiServiceImpl()
    }

    override fun getCompanyApiService(): CompanyApiService {
        return CompanyApiServiceImpl()
    }

    override fun getCompanyOfficeApiService(): CompanyOfficeApiService {
        return CompanyOfficeApiServiceImpl()
    }

    override fun getCredentialApiService(): CredentialApiService {
        return CredentialApiServiceImpl()
    }
}