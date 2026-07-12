package com.orka.myfinances.application.storages

import com.orka.myfinances.data.database.daos.DefaultsDao
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.storages.defaults.DefaultsStorage

class DefaultsStorageImpl(private val dao: DefaultsDao) : DefaultsStorage {
    override suspend fun getDefaultBranchId(): Id? {
        return dao.getDefaultBranchId()?.let { Id(it) }
    }

    override suspend fun setDefaultBranchId(id: Id) {
        dao.setDefaultBranchId(id.value)
    }

    override suspend fun clear() {
        dao.clear()
    }
}