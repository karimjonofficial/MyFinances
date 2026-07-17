package com.orka.myfinances.data.repositories.defaults

import com.orka.myfinances.data.models.Id

interface GetDefaultCategory {
    suspend fun getDefaultCategoryId(): Id?
}