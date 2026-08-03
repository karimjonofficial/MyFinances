package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.ui.models.screen.AddReceiveScreenModel

fun FolderDto.toScreenModel(): AddReceiveScreenModel {
    return AddReceiveScreenModel(name)
}
