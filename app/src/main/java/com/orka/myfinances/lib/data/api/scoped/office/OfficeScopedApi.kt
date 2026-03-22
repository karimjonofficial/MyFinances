package com.orka.myfinances.lib.data.api.scoped.office

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.api.Api

interface OfficeScopedApi : Api {
    val office: Office
}