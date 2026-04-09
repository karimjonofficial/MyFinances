package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenModel

fun FolderApiModel.toScreenModel(): AddReceiveScreenModel {
    return AddReceiveScreenModel(name)
}