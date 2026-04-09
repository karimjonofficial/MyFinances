package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.zipped.SessionModel

fun Session.toModel(): SessionModel {
    return SessionModel(
        credentials = credentials,
        officeId = officeId.value
    )
}