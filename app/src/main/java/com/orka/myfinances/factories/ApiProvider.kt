package com.orka.myfinances.factories

import com.orka.myfinances.data.api.CompanyApiService
import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.api.UserApiService

interface ApiProvider {
    fun getUserApiService(): UserApiService
    fun getCompanyApiService(): CompanyApiService
    fun officeApi(): OfficeApi
    fun getCredentialApiService(): CredentialApiService
}