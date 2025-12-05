package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination

@OptIn(ExperimentalMaterial3Api::class)
fun productEntry(
    modifier: Modifier,
    destination: Destination.Product
): NavEntry<Destination> = entry(destination) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = destination.product.name) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.scaffoldPadding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.product))
        }
    }
}