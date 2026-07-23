package com.orka.myfinances.ui.screens.settings.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.components.buttons.SettingsButton

fun LazyListScope.HomeContentGroup(
    interactor: SettingsScreenInteractor,
    state: State<SettingsScreenModel>
) {
    item {
        SettingsButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.pinned_categories),
            topClipped = true,
            bottomClipped = true,
            leadingIcon = painterResource(R.drawable.keep),
            trailingIcon = painterResource(R.drawable.arrow_right),
            hasDivider = false,
            enabled = state is State.Success,
            onClick = interactor::toPinnedCategories,
        )
    }
}