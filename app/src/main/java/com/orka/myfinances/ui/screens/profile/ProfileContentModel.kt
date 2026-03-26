package com.orka.myfinances.ui.screens.profile

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.User

data class ProfileContentModel(
    val offices: List<Office>,
    val user: User
)
