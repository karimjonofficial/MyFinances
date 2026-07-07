package com.orka.myfinances.lib.data.api.scoped.branch

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.Api

interface BranchScopedApi<T> : Api<T> {
    val branchId: Id
}