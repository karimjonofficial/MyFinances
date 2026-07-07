package com.orka.myfinances.testFixtures.resources.models

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.testFixtures.resources.refresh
import com.orka.myfinances.testFixtures.resources.token

val credentials1 = Credentials(
    access = token,
    refresh = refresh
)
