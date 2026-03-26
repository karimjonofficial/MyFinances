package com.orka.myfinances.ui.screens.profile

import com.orka.myfinances.R

fun options(interactor: ProfileInteractor): List<ProfileOption> {
    return listOf(
        ProfileOption(
            index = 0,
            titleRes = R.string.settings,
            action = interactor::settings
        ),
        ProfileOption(
            index = 1,
            titleRes = R.string.history,
            action = interactor::history
        ),
        ProfileOption(
            index = 2,
            titleRes = R.string.templates,
            action = interactor::templates
        ),
        ProfileOption(
            index = 3,
            titleRes = R.string.clients,
            action = interactor::clients
        ),
        ProfileOption(
            index = 4,
            titleRes = R.string.orders,
            action = interactor::orders
        ),
        ProfileOption(
            index = 5,
            titleRes = R.string.debts,
            action = interactor::debts
        )
    )
}