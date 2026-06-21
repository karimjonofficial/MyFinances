package com.orka.myfinances.ui.screens.product.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.ui.components.CommentTextField
import com.orka.myfinances.lib.ui.components.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.FooterSpacer
import com.orka.myfinances.lib.ui.components.IntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryItemModel

@Composable
fun ProductTitleEditorContent(
    modifier: Modifier = Modifier,
    model: AddProductTitleScreenModel,
    selectedCategory: MutableState<CategoryItemModel?>,
    name: MutableState<String>,
    price: MutableState<Int?>,
    salePrice: MutableState<Int?>,
    exposedPrice: MutableState<Int?>,
    description: MutableState<String?>,
    properties: SnapshotStateList<PropertyModel<*>?>
) {
    val menuVisible = rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val currentCategory = selectedCategory.value
    val fields = currentCategory?.template?.fields.orEmpty()
    val categories = model.categories
    val fullWidth = Modifier.fillMaxWidth()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        VerticalSpacer(8)
        TextField(
            modifier = fullWidth,
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text(text = stringResource(R.string.name)) }
        )

        VerticalSpacer(8)
        ExposedDropDownTextField(
            modifier = fullWidth,
            text = currentCategory?.title ?: stringResource(R.string.select_category),
            label = stringResource(R.string.category),
            menuExpanded = menuVisible.value,
            onExpandChange = { menuVisible.value = it },
            onDismissRequested = { menuVisible.value = false },
            items = categories,
            itemText = { it.title },
            onItemSelected = {
                selectedCategory.value = it
                menuVisible.value = false
            }
        )

        if (currentCategory != null) {
            VerticalSpacer(8)
            PropertiesCard(
                modifier = fullWidth,
                fields = fields,
                properties = properties,
                initialProperties = if (currentCategory.id == model.categoryId) model.initialProperties else emptyList()
            )
        }

        VerticalSpacer(8)
        IntegerTextField(
            modifier = fullWidth,
            value = price.value,
            onValueChange = { price.value = it },
            label = { Text(text = stringResource(R.string.price)) }
        )

        VerticalSpacer(8)
        IntegerTextField(
            modifier = fullWidth,
            value = salePrice.value,
            onValueChange = { salePrice.value = it },
            label = { Text(text = stringResource(R.string.sale_price)) }
        )

        VerticalSpacer(8)
        IntegerTextField(
            modifier = fullWidth,
            value = exposedPrice.value,
            onValueChange = { exposedPrice.value = it },
            label = { Text(text = stringResource(R.string.exposed_price)) }
        )

        VerticalSpacer(8)
        CommentTextField(
            modifier = fullWidth,
            value = description.value ?: "",
            onValueChange = { description.value = it },
            label = stringResource(R.string.description)
        )
        FooterSpacer()
    }
}
