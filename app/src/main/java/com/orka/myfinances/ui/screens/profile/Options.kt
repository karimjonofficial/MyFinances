package com.orka.myfinances.ui.screens.profile

import com.orka.myfinances.R

fun options(interactor: ProfileInteractor): List<ProfileOption> {
    return listOf(
        ProfileOption(
            index = 0,
            titleRes = R.string.settings,
            iconRes = R.drawable.settings_outlined,
            action = interactor::settings
        ),
        ProfileOption(
            index = 1,
            titleRes = R.string.history,
            iconRes = R.drawable.schedule,
            action = interactor::history
        ),
        ProfileOption(
            index = 2,
            titleRes = R.string.templates,
            iconRes = R.drawable.docs,
            action = interactor::templates
        ),
        ProfileOption(
            index = 3,
            titleRes = R.string.clients,
            iconRes = R.drawable.person,
            action = interactor::clients
        ),
        ProfileOption(
            index = 4,
            titleRes = R.string.orders,
            iconRes = R.drawable.shopping_bag_outlined,
            action = interactor::orders
        ),
        ProfileOption(
            index = 5,
            titleRes = R.string.debts,
            iconRes = R.drawable.money,
            action = interactor::debts
        ),
        ProfileOption(
            index = 6,
            titleRes = R.string.option,
            iconRes = R.drawable.check,
            action = {}
        ),
        ProfileOption(
            index = 7,
            titleRes = R.string.option,
            iconRes = R.drawable.check,
            action = {}
        ),
        ProfileOption(
            index = 8,
            titleRes = R.string.option,
            iconRes = R.drawable.check,
            action = {}
        ),
        ProfileOption(
            index = 9,
            titleRes = R.string.option,
            iconRes = R.drawable.check,
            action = {}
        ),
        ProfileOption(
            index = 10,
            titleRes = R.string.option,
            iconRes = R.drawable.check,
            action = {}
        )
    )
}