package com.orka.myfinances.lib.extensions.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R

@Composable
fun String?.description(defaultId: Int = R.string.no_description_provided): String {
    return if(this.isNullOrBlank()) stringResource(defaultId) else this
}

@Composable
fun String?.address(defaultId: Int = R.string.no_address): String {
    return if(this.isNullOrBlank()) stringResource(defaultId) else this
}

@Composable
fun String?.phone(defaultId: Int = R.string.no_phone_provided): String {
    return if(this.isNullOrBlank()) stringResource(defaultId) else this
}