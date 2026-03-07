package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.IconRes
import com.orka.myfinances.lib.ui.models.NavItem
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketInteractor
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketState
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersInteractor
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersState
import com.orka.myfinances.ui.screens.home.viewmodel.folder.TemplateState
import com.orka.myfinances.ui.screens.home.viewmodel.profile.ProfileInteractor
import kotlinx.coroutines.flow.StateFlow
import com.orka.myfinances.lib.ui.Scaffold as AppScaffold

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    session: Session,
    sessionManager: SessionManager,
    foldersInteractor: FoldersInteractor,
    foldersState: StateFlow<FoldersState>,
    folderDialogState: StateFlow<TemplateState>,
    basketInteractor: BasketInteractor,
    basketState: StateFlow<BasketState>,
    profileInteractor: ProfileInteractor,
    profileState: StateFlow<State>
) {
    val navItems = listOf(
        NavItem(
            index = 0,
            name = stringResource(R.string.home),
            iconRes = IconRes(
                selected = R.drawable.home_filled,
                unSelected = R.drawable.home_outlined
            )
        ),
        NavItem(
            index = 1,
            name = stringResource(R.string.basket),
            iconRes = IconRes(
                unSelected = R.drawable.shopping_cart_outlined,
                selected = R.drawable.shopping_cart_filled
            )
        ),
        NavItem(
            index = 2,
            name = stringResource(R.string.profile),
            iconRes = IconRes(
                unSelected = R.drawable.account_circle_outlined,
                selected = R.drawable.account_circle_filled
            )
        )
    )
    val navState = rememberSaveable { mutableIntStateOf(0) }
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    val basketStateValue by basketState.collectAsState()
    val foldersStateValue by foldersState.collectAsState()

    LaunchedEffect(navState.intValue, foldersStateValue) {
        if (navState.intValue == 0 && foldersStateValue is FoldersState.Initial) {
            foldersInteractor.refresh()
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWide = maxWidth >= 880.dp

        AppScaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                        )
                    )
                ),
            bottomBar = {
                if (!isWide) {
                    CompactNavDock(
                        items = navItems,
                        selectedIndex = navState.intValue,
                        onSelected = { navState.intValue = it }
                    )
                }
            }
        ) { paddingValues ->
            val baseModifier = Modifier.scaffoldPadding(paddingValues)

            if (isWide) {
                Row(
                    modifier = baseModifier
                        .fillMaxSize()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    ExpandedNavigationRail(
                        items = navItems,
                        selectedIndex = navState.intValue,
                        onSelected = { navState.intValue = it }
                    )

                    DashboardPane(
                        modifier = Modifier.weight(1f),
                        navIndex = navState.intValue,
                        basketState = basketStateValue,
                        onAddClick = { dialogVisible.value = true },
                        onSearchClick = { foldersInteractor.navigateToSearch() },
                        onNotificationsClick = { foldersInteractor.navigateToNotifications() },
                        onClearBasketClick = { basketInteractor.clear() },
                        foldersState = foldersState,
                        foldersInteractor = foldersInteractor,
                        basketInteractor = basketInteractor,
                        profileState = profileState,
                        profileInteractor = profileInteractor,
                        session = session,
                        sessionManager = sessionManager
                    )
                }
            } else {
                DashboardPane(
                    modifier = baseModifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    navIndex = navState.intValue,
                    basketState = basketStateValue,
                    onAddClick = { dialogVisible.value = true },
                    onSearchClick = { foldersInteractor.navigateToSearch() },
                    onNotificationsClick = { foldersInteractor.navigateToNotifications() },
                    onClearBasketClick = { basketInteractor.clear() },
                    foldersState = foldersState,
                    foldersInteractor = foldersInteractor,
                    basketInteractor = basketInteractor,
                    profileState = profileState,
                    profileInteractor = profileInteractor,
                    session = session,
                    sessionManager = sessionManager
                )
            }

            if (dialogVisible.value) {
                val dialogState by folderDialogState.collectAsState()

                AddFolderDialog(
                    state = dialogState,
                    dismissRequest = { dialogVisible.value = false },
                    onAddTemplateClick = {
                        foldersInteractor.navigateToAddTemplate()
                        dialogVisible.value = false
                    },
                    onSuccess = { name, type, templateId ->
                        foldersInteractor.addFolder(name, type, templateId)
                        dialogVisible.value = false
                    },
                    onCancel = { dialogVisible.value = false }
                )
            }
        }
    }
}

@Composable
private fun DashboardPane(
    modifier: Modifier,
    navIndex: Int,
    basketState: BasketState,
    onAddClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onClearBasketClick: () -> Unit,
    foldersState: StateFlow<FoldersState>,
    foldersInteractor: FoldersInteractor,
    basketInteractor: BasketInteractor,
    profileState: StateFlow<State>,
    profileInteractor: ProfileInteractor,
    session: Session,
    sessionManager: SessionManager
) {
    val title = when (navIndex) {
        0 -> stringResource(R.string.app_name)
        1 -> stringResource(R.string.basket)
        else -> stringResource(R.string.profile)
    }

    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            DashboardHeader(
                title = title,
                navIndex = navIndex,
                basketState = basketState,
                onAddClick = onAddClick,
                onSearchClick = onSearchClick,
                onNotificationsClick = onNotificationsClick,
                onClearBasketClick = onClearBasketClick
            )

            val contentModifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)

            when (navIndex) {
                0 -> {
                    val state by foldersState.collectAsState()
                    FoldersContent(
                        modifier = contentModifier,
                        state = state,
                        interactor = foldersInteractor
                    )
                }

                1 -> {
                    BasketContent(
                        modifier = contentModifier,
                        state = basketState,
                        interactor = basketInteractor
                    )
                }

                else -> {
                    val state by profileState.collectAsState()
                    ProfileContent(
                        modifier = contentModifier,
                        session = session,
                        state = state,
                        interactor = profileInteractor,
                        sessionManager = sessionManager
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardHeader(
    title: String,
    navIndex: Int,
    basketState: BasketState,
    onAddClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onClearBasketClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = when (navIndex) {
                    0 -> "Operations overview"
                    1 -> "Review and submit items"
                    else -> "Profile and navigation"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            when (navIndex) {
                0 -> {
                    HeaderAction(icon = R.drawable.add, description = stringResource(R.string.add), action = onAddClick)
                    HeaderAction(icon = R.drawable.search, description = stringResource(R.string.search), action = onSearchClick)
                    HeaderAction(icon = R.drawable.notifications, description = stringResource(R.string.notifications), action = onNotificationsClick)
                }

                1 -> {
                    if (basketState is BasketState.Success && basketState.items.isNotEmpty()) {
                        HeaderAction(icon = R.drawable.delete_outlined, description = stringResource(R.string.clear), action = onClearBasketClick)
                    }
                }

                else -> {
                    HeaderAction(icon = R.drawable.edit_outlined, description = stringResource(R.string.edit), action = {})
                }
            }
        }
    }
}

@Composable
private fun HeaderAction(icon: Int, description: String, action: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        IconButton(onClick = action) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(icon),
                contentDescription = description,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CompactNavDock(
    items: List<NavItem>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items.forEach { item ->
                    val selected = item.index == selectedIndex
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (selected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            androidx.compose.ui.graphics.Color.Transparent
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = { onSelected(item.index) }
                            ) {
                                Icon(
                                    painter = painterResource(
                                        if (selected) item.iconRes.selected else item.iconRes.unSelected
                                    ),
                                    contentDescription = item.name,
                                    tint = if (selected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }
                            if (selected) {
                                HorizontalSpacer(6)
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun ExpandedNavigationRail(
    items: List<NavItem>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .width(112.dp)
            .fillMaxHeight(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            VerticalSpacer(8)
            NavigationRail(
                containerColor = androidx.compose.ui.graphics.Color.Transparent,
                header = {
                    Icon(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = stringResource(R.string.logo),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    VerticalSpacer(16)
                }
            ) {
                items.forEach { item ->
                    NavigationRailItem(
                        selected = item.index == selectedIndex,
                        onClick = { onSelected(item.index) },
                        icon = {
                            Icon(
                                painter = painterResource(
                                    if (item.index == selectedIndex) item.iconRes.selected else item.iconRes.unSelected
                                ),
                                contentDescription = item.name
                            )
                        },
                        label = { Text(item.name) },
                        colors = NavigationRailItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
            VerticalSpacer(8)
        }
    }
}



