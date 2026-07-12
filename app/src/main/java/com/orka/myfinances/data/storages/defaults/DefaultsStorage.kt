package com.orka.myfinances.data.storages.defaults

import com.orka.myfinances.data.models.Id

interface DefaultsStorage {
    suspend fun getDefaultBranchId(): Id?
    suspend fun setDefaultBranchId(id: Id)
    suspend fun clear()
}