package com.orka.myfinances.application.storages.credentials

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.database.entities.CredentialsEntity

fun CredentialsEntity.toCredentials() = Credentials(
    access = access,
    refresh = refresh
)