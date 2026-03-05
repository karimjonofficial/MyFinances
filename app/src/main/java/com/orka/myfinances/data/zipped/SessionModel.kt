package com.orka.myfinances.data.zipped

import com.orka.myfinances.data.models.Credentials

data class SessionModel(
    val credentials: Credentials,
    val officeId: Int
)