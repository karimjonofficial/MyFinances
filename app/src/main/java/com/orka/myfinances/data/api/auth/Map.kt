package com.orka.myfinances.data.api.auth

import com.orka.myfinances.data.api.auth.models.response.CredentialsApiModel
import com.orka.myfinances.data.models.Credentials

fun CredentialsApiModel.map(): Credentials {
    return Credentials(
        access = access,
        refresh = refresh
    )
}