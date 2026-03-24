package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.models.ScreenTab
import kotlinx.coroutines.launch

@Composable
fun MultipleTabScreen(
    modifier: Modifier = Modifier,
    title: String,
    tabs: List<ScreenTab>
) {
    Scaffold(
        modifier = modifier,
        title = title,
    ) { paddingValues ->
        Column(modifier = Modifier.scaffoldPadding(paddingValues)) {
            val selectedTab = rememberSaveable { mutableIntStateOf(0) }
            val pagerState = rememberPagerState(pageCount = { tabs.size })
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }
                    .collect { page ->
                        selectedTab.intValue = page
                    }
            }

            PrimaryTabRow(selectedTabIndex = selectedTab.intValue) {
                tabs.forEach {
                    Tab(
                        selected = selectedTab.intValue == it.index,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(it.index) }
                            selectedTab.intValue = it.index
                        },
                        text = { Text(text = it.title) }
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                state = pagerState,
            ) { page ->
                tabs[page].content?.invoke(Modifier.fillMaxSize())
            }
        }
    }
}