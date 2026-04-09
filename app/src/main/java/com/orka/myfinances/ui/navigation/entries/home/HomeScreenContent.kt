package com.orka.myfinances.ui.navigation.entries.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.myfinances.application.viewmodels.basket.BasketContentViewModel
import com.orka.myfinances.application.viewmodels.folder.home.FoldersContentViewModel
import com.orka.myfinances.application.viewmodels.profile.ProfileContentViewModel
import com.orka.myfinances.application.viewmodels.template.bottomsheet.TemplateBottomSheetViewModel
import com.orka.myfinances.ui.screens.basket.BasketContent
import com.orka.myfinances.ui.screens.folder.components.AddFolderDialog
import com.orka.myfinances.ui.screens.folder.home.FoldersContent
import com.orka.myfinances.ui.screens.folder.models.TemplateItemModel
import com.orka.myfinances.ui.screens.profile.ProfileContent
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenContent(
    modifier: Modifier,
    index: Int,
    officeId: String,
    foldersViewModel: FoldersContentViewModel,
    dialogVisible: MutableState<Boolean>,
    basketViewModel: BasketContentViewModel,
    profileViewModel: ProfileContentViewModel,
    sheetViewModel: TemplateBottomSheetViewModel
) {
    val sheetVisible = rememberSaveable { mutableStateOf(false) }
    when (index) {
        0 -> {
            LaunchedEffect(officeId) {
                foldersViewModel.initialize()
                sheetViewModel.initialize()
            }

            val sheetState = rememberModalBottomSheetState()
            val coroutineScope = rememberCoroutineScope()
            val foldersState = foldersViewModel.uiState.collectAsState()
            val template = retain { mutableStateOf<TemplateItemModel?>(null) }

            FoldersContent(
                modifier = modifier,
                state = foldersState.value,
                interactor = foldersViewModel
            )

            if (dialogVisible.value) {
                AddFolderDialog(
                    dismissRequest = { dialogVisible.value = false },
                    onUnfoldTemplates = { sheetVisible.value = true },
                    onSuccess = { name, type, templateId ->
                        foldersViewModel.addFolder(name, type, templateId)
                        dialogVisible.value = false
                    },
                    template = template.value,
                    onCancel = { dialogVisible.value = false }
                )
            }

            if (sheetVisible.value) {
                val state = sheetViewModel.uiState.collectAsState()
                SelectTemplateBottomSheet(
                    onDismissRequest = {
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                sheetVisible.value = false
                            }
                        }
                    },
                    sheetState = sheetState,
                    state = state.value,
                    onSelected = {
                        template.value = it
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                sheetVisible.value = false
                            }
                        }
                    },
                    selectedItem = template.value,
                    onLoadMore = sheetViewModel::loadMore
                )
            }

        }

        1 -> {
            val basketState = basketViewModel.uiState.collectAsState()

            LaunchedEffect(officeId) {
                basketViewModel.initialize()
            }

            BasketContent(
                modifier = modifier,
                state = basketState.value,
                interactor = basketViewModel
            )
        }

        2 -> {
            val profileState = profileViewModel.uiState.collectAsState()

            LaunchedEffect(officeId) {
                profileViewModel.initialize()
            }

            ProfileContent(
                modifier = modifier,
                state = profileState.value,
                interactor = profileViewModel
            )
        }
    }
}