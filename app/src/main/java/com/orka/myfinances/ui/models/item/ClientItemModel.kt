package com.orka.myfinances.ui.models.item

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.BottomSheetItemModel

data class ClientItemModel(
    override val id: Id,
    override val title: String
) : BottomSheetItemModel