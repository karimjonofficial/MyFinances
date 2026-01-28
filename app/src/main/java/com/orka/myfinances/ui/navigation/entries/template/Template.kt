package com.orka.myfinances.ui.navigation.entries.template

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.templates.TemplateScreen

fun templateEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Template
): NavEntry<Destination> = entry(destination) {

    TemplateScreen(
        modifier = modifier,
        template = destination.template
    )
}