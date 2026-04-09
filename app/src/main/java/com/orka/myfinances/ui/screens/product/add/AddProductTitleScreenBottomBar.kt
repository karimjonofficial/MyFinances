package com.orka.myfinances.ui.screens.product.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryItemModel

@Composable
fun AddProductTitleScreenBottomBar(
    name: MutableState<String>,
    price: MutableState<Int?>,
    salePrice: MutableState<Int?>,
    propertiesValid: Boolean,
    selectedCategory: CategoryItemModel?,
    interactor: AddProductTitleScreenInteractor,
    properties: List<PropertyModel<*>?>,
    description: MutableState<String?>
) {
    BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                enabled = name.value.isNotEmpty() &&
                        price.value != null &&
                        salePrice.value != null && propertiesValid &&
                        selectedCategory != null,
                onClick = {
                    selectedCategory?.let { category ->
                        interactor.addProductTitle(
                            properties = properties,
                            name = name.value,
                            price = price.value,
                            salePrice = salePrice.value,
                            description = description.value,
                            categoryId = category.id,
                        )
                    }
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}