package com.orka.myfinances.data.repositories.defaults

import com.orka.myfinances.data.models.Id

interface SetDefaultCategory {
    suspend fun setDefaultCategoryId(id: Id)
}