package com.orka.myfinances.ui.screens.product.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryItemModel

@Composable
fun ProductTitleEditorScreen(
    modifier: Modifier = Modifier,
    title: String,
    state: State<AddProductTitleScreenModel>,
    onRetry: () -> Unit,
    onSave: (List<PropertyModel<*>?>, String, Int?, Int?, Int?, String?, Id) -> Unit
) {
    val model = state.value
    val selectedCategory = retain { mutableStateOf<CategoryItemModel?>(null) }
    val name = rememberSaveable { mutableStateOf("") }
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val salePrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val exposedPrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }

    val properties = remember(selectedCategory.value?.id) {
        mutableStateListOf<PropertyModel<*>?>().apply {
            if (model != null && selectedCategory.value?.id == model.categoryId) {
                addAll(model.initialProperties)
            }
        }
    }

    LaunchedEffect(model) {
        if (model != null) {
            if (name.value.isEmpty()) name.value = model.initialName
            if (price.value == null) price.value = model.initialPrice
            if (salePrice.value == null) salePrice.value = model.initialSalePrice
            if (exposedPrice.value == null) exposedPrice.value = model.initialExposedPrice
            if (description.value == null) description.value = model.initialDescription

            if (selectedCategory.value == null || model.categories.none { it.id == selectedCategory.value?.id }) {
                selectedCategory.value = model.categories.find { it.id == model.categoryId }
                    ?: model.categories.firstOrNull()
            }
        }
    }

    val currentCategory = selectedCategory.value
    val fields = currentCategory?.template?.fields.orEmpty()
    val propertiesValid = properties.all { it != null } && properties.size == fields.size

    StatefulScreen(
        modifier = modifier,
        title = title,
        bottomBar = {
            AddProductTitleScreenBottomBar(
                name = name,
                price = price,
                salePrice = salePrice,
                exposedPrice = exposedPrice,
                propertiesValid = propertiesValid,
                selectedCategory = currentCategory,
                properties = properties,
                description = description,
                onSave = onSave
            )
        },
        state = state,
        onRetry = onRetry,
    ) { innerModifier, successModel ->
        ProductTitleEditorContent(
            modifier = innerModifier,
            model = successModel,
            selectedCategory = selectedCategory,
            name = name,
            price = price,
            salePrice = salePrice,
            exposedPrice = exposedPrice,
            description = description,
            properties = properties
        )
    }
}
