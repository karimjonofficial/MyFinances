package com.orka.myfinances.lib.data.api.scoped.company

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.Api

interface CompanyScopedApi<T> : Api<T> {
    val companyId: Id
}