package com.orka.myfinances.lib.data.api.scoped.office

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.Api

interface OfficeScopedApi<T> : Api<T> {
    val officeId: Id
}