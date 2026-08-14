package com.orka.myfinances.lib.ui.components.spacer

import androidx.compose.foundation.lazy.LazyListScope

fun LazyListScope.LazyFooterSpacer(height: Int = 16) {
    item {
        FooterSpacer(height)
    }
}