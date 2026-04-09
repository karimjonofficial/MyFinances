package com.orka.myfinances.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.R

@Composable
fun UserIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        painter = painterResource(R.drawable.account_circle_outlined),
        tint = MaterialTheme.colorScheme.secondary,
        contentDescription = null
    )
}