package com.orka.myfinances.ui.screens.profile

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.content.ProfileContentModel

@Composable
fun NameText(
    modifier: Modifier = Modifier,
    state: State<ProfileContentModel>,
    style: TextStyle = MaterialTheme.typography.titleLarge
) {
    val name = if (state.value != null)
        state.value!!.name.ifBlank { stringResource(R.string.name_left_blank) }
    else stringResource(R.string.loading)

    Text(
        modifier = modifier,
        text = name,
        fontWeight = FontWeight.Bold,
        style = style,
        color = MaterialTheme.colorScheme.onSecondaryContainer
    )
}
