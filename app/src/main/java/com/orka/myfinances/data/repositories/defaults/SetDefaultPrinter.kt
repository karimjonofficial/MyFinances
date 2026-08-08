package com.orka.myfinances.data.repositories.defaults

import com.orka.myfinances.data.models.Id

interface SetDefaultPrinter {
    suspend fun setDefaultPrinter(id: Id)
}