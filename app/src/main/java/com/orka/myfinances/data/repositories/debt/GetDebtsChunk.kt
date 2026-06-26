package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.lib.viewmodel.Chunk

fun interface GetDebtsChunk {
    suspend fun getDebtsChunk(
        size: Int,
        page: Int,
        isCompleted: Boolean,
        query: String?
    ): Chunk<DebtDto>?
}
