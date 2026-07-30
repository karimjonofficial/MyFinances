package com.orka.myfinances.ui.screens.settings.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.components.buttons.SettingsButton

fun LazyListScope.DefaultsGroup(
    interactor: SettingsScreenInteractor,
    state: State<SettingsScreenModel>
) {
    val title = state.value?.defaultCategory
    item {
        val color = if (state is State.Loading || title != null)
            MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.error

        VerticalSpacer(4)
        SettingsButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.default_category),
            topClipped = true,
            bottomClipped = true,
            value = if (state is State.Loading) stringResource(R.string.loading)
            else title ?: stringResource(R.string.default_category_is_not_set_yet),
            leadingIcon = painterResource(R.drawable.category),
            valueColor = color,
            hasDivider = false,
            enabled = state is State.Success,
            onClick = interactor::toSelectDefaultCategory,
        )
    }
}