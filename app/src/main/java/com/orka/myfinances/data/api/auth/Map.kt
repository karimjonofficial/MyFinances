package com.orka.myfinances.data.api.auth

import com.orka.myfinances.data.models.Credentials

fun CredentialsModel.map(): Credentials {
    return Credentials(
        access = access,
        refresh = refresh
    )
}