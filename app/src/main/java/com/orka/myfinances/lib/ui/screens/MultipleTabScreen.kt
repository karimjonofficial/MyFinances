package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.models.ScreenTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipleTabScreen(
    modifier: Modifier = Modifier,
    title: String,
    tabs: List<ScreenTab>
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = title) }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.scaffoldPadding(paddingValues)) {
            val selectedTab = rememberSaveable { mutableIntStateOf(0) }
            val selectedTabValue = selectedTab.intValue

            PrimaryTabRow(selectedTabIndex = selectedTabValue) {
                tabs.forEach {
                    Tab(
                        selected = selectedTabValue == it.index,
                        onClick = { selectedTab.intValue = it.index },
                        text = { Text(text = it.title) }
                    )
                }
            }

            tabs[selectedTabValue].content?.invoke(Modifier)
        }
    }
}