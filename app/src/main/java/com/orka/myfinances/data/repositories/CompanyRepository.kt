package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.Company

interface CompanyRepository {
    suspend fun get(): Company?
}