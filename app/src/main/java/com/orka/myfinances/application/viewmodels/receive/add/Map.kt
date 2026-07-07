package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenModel

fun FolderDto.toScreenModel(): AddReceiveScreenModel {
    return AddReceiveScreenModel(name)
}
