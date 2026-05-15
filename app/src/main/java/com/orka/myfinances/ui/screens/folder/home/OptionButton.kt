package com.orka.myfinances.ui.screens.folder.home

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.orka.myfinances.lib.ui.components.HorizontalSpacer

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onClick: () -> Unit
) {
    FilledTonalButton(
        modifier = modifier,
        contentPadding = ButtonDefaults.contentPaddingFor(
            buttonHeight = ButtonDefaults.MediumContainerHeight,
            hasStartIcon = true
        ),
        onClick = onClick
    ) {
        Icon(
            painter = painter,
            contentDescription = text
        )

        HorizontalSpacer(4)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )
    }
}