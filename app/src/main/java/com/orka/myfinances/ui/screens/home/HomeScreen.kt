package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.models.NavItem
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable (index: Int) -> Unit,
    content: @Composable (modifier: Modifier, index: Int) -> Unit
) {
    val navItems = navItems()
    val pagerState = rememberPagerState(pageCount = { navItems.size })
    val coroutineScope = rememberCoroutineScope()

    fun NavItem.getIconRes() = if (pagerState.currentPage == index) iconRes.selected else iconRes.unSelected

    Scaffold(
        modifier = modifier,
        topBar = { topBar(pagerState.currentPage) },
        bottomBar = {
            NavigationBar {
                navItems.forEach { item ->
                    NavigationBarItem(
                        selected = item.index == pagerState.currentPage,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(item.index)
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.getIconRes()),
                                contentDescription = item.name
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            beyondViewportPageCount = 2
        ) { page ->
            content(Modifier.scaffoldPadding(paddingValues), page)
        }
    }
}
