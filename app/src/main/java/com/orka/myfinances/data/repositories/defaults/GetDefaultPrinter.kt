package com.orka.myfinances.data.repositories.defaults

import com.orka.myfinances.data.models.Id

interface GetDefaultPrinter {
    suspend fun getDefaultPrinter(): Id?
}