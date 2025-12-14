package com.orka.myfinances.testFixtures.resources.models

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.testFixtures.resources.refresh
import com.orka.myfinances.testFixtures.resources.token

val credential = Credential(
    token = token,
    refresh = refresh
)