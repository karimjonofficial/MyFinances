package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.models.Credentials

fun CredentialsModel.map(): Credentials {
    return Credentials(
        access = access,
        refresh = refresh
    )
}