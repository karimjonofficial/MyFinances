package com.orka.myfinances.ui.screens.debt.history

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel

interface DebtsHistoryContentInteractor : StateFul, ChunkViewModel {
    fun select(item: DebtUiModel)
}