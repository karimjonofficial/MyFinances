package com.orka.myfinances.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.spacer.HorizontalSpacer
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.content.ProfileContentModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier,
    state: State<ProfileContentModel>,
    scrollState: LazyListState,
    interactor: ProfileInteractor
) {
    val progress by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex > 0) 1f
            else {
                val threshold = 500f
                (scrollState.firstVisibleItemScrollOffset.toFloat() / threshold).coerceIn(0f, 1f)
            }
        }
    }

    TopAppBar(
        modifier = modifier,
        title = {
            Box(contentAlignment = Alignment.CenterStart) {
                Text(
                    text = stringResource(R.string.profile),
                    modifier = Modifier.alpha(1f - progress)
                )

                val infoAlpha = (progress - 0.5f).coerceIn(0f, 0.5f) / 0.5f
                if (infoAlpha > 0f) {
                    Row(
                        modifier = Modifier.alpha(infoAlpha),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UserIcon(Modifier.size(40.dp))
                        HorizontalSpacer(12)
                        Column {
                            NameText(
                                state = state,
                                style = MaterialTheme.typography.titleMedium
                            )
                            PhoneText(
                                state = state,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = interactor::logout) {
                Icon(
                    painterResource(R.drawable.logout),
                    contentDescription = stringResource(R.string.exit)
                )
            }
        }
    )
}
