package com.orka.myfinances.factories

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.api.CompanyOfficeApiService
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.api.UserApiService

interface ApiProvider {
    fun getUserApiService(): UserApiService
    fun getCompanyApiService(): CompanyApiService
    fun getCompanyOfficeApiService(): CompanyOfficeApiService
    fun getCredentialApiService(): CredentialApiService
}