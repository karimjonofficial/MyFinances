package com.orka.myfinances.ui.screens.debt.history

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel

interface DebtsHistoryContentInteractor : Refreshable, ChunkViewModel {
    fun select(item: DebtUiModel)
}