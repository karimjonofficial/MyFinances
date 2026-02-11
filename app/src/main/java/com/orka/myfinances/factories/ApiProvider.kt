package com.orka.myfinances.factories

import com.orka.myfinances.data.api.CompanyApi
import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.api.CredentialApi
import com.orka.myfinances.data.api.UserApi

interface ApiProvider {
    fun getUserApiService(): UserApi
    fun getCompanyApiService(): CompanyApi
    fun officeApi(): OfficeApi
    fun getCredentialApiService(): CredentialApi
}