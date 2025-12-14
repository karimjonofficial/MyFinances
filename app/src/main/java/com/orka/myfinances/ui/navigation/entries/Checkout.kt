package com.orka.myfinances.ui.navigation.entries

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.lib.ui.screens.LazyColumn
import com.orka.myfinances.ui.managers.navigation.Destination

@OptIn(ExperimentalMaterial3Api::class)
fun checkoutEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Checkout,
): NavEntry<Destination> = entry(destination) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.checkout)) })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.scaffoldPadding(paddingValues)) {
            LazyColumn(
                items = destination.items,
                item = { modifier, item ->
                    Text(
                        modifier = modifier,
                        text = item.product.title.name
                    )
                }
            )
        }
    }
}