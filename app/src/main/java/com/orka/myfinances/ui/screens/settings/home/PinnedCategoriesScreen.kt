package com.orka.myfinances.ui.screens.settings.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.lib.ui.screens.SelectionScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.item.CategoryItemModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun PinnedCategoriesScreen(
    modifier: Modifier = Modifier,
    state: State<Map<String, List<CategoryItemModel>>>,
    selectedState: State<List<Int>>,
    interactor: PinnedCategoriesScreenInteractor,
    refresh: () -> Unit
) {
    val selectedIds = rememberSaveable { mutableStateListOf<Int>() }

    LaunchedEffect(selectedState) {
        if (selectedState is State.Success) {
            selectedIds.clear()
            selectedIds.addAll(selectedState.value)
        }
    }

    SelectionScreen(
        modifier = modifier,
        bottomBar = { state ->
            SingleActionBottomBar(
                buttonEnabled = state is State.Success,
                action = { interactor.save(selectedIds) }
            )
        },
        title = stringResource(R.string.pinned_categories),
        state = state,
        isSelected = { selectedIds.contains(it.id.value) },
        onSelect = { model, selected ->
            if (selected)
                selectedIds.remove(model.id.value)
            else selectedIds.add(model.id.value)
        },
        retry = refresh
    )
}

@DefaultPreview
@Composable
private fun PinnedCategoriesScreenPreview() {
    MyFinancesTheme {
        PinnedCategoriesScreen(
            selectedState = State.Success(listOf(1)),
            state = State.Success(
                mapOf(
                    "S" to listOf(
                        CategoryItemModel(
                            id = Id(1),
                            title = "Smartphones",
                            description = "Latest mobile phones"
                        ),
                        CategoryItemModel(
                            id = Id(2),
                            title = "Smartwatches",
                            description = "Wearable gadgets"
                        )
                    ),
                    "L" to listOf(
                        CategoryItemModel(
                            id = Id(3),
                            title = "Laptops",
                            description = "Powerful portable computers"
                        )
                    )
                )
            ),
            interactor = PinnedCategoriesScreenInteractor.dummy,
            refresh = {}
        )
    }
}