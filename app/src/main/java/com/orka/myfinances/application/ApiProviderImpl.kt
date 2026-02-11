package com.orka.myfinances.application

import com.orka.myfinances.data.api.CompanyApi
import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.api.CredentialApi
import com.orka.myfinances.data.api.UserApi
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.fixtures.data.api.CompanyApiImpl
import com.orka.myfinances.fixtures.data.api.OfficeApiImpl
import com.orka.myfinances.fixtures.data.api.CredentialApiImpl
import com.orka.myfinances.fixtures.data.api.UserApiImpl

class ApiProviderImpl : ApiProvider {
    override fun getUserApiService(): UserApi {
        return UserApiImpl()
    }

    override fun getCompanyApiService(): CompanyApi {
        return CompanyApiImpl()
    }

    override fun officeApi(): OfficeApi {
        return OfficeApiImpl()
    }

    override fun getCredentialApiService(): CredentialApi {
        return CredentialApiImpl()
    }
}