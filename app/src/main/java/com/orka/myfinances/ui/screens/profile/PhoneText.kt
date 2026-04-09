package com.orka.myfinances.ui.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.profile.models.ProfileContentModel

@Composable
fun PhoneText(
    modifier: Modifier = Modifier,
    state: State<ProfileContentModel>
) {
    val phone = if (state is State.Success)
        state.value.phone ?: stringResource(R.string.no_phone_number)
    else stringResource(R.string.loading)

    Text(
        modifier = modifier,
        text = phone
    )
}