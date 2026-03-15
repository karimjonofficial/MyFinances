package com.orka.myfinances.ui.screens.product.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.fixtures.resources.models.folder.categories
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenState
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun AddProductTitleScreen(
    modifier: Modifier = Modifier,
    categoryId: Id,
    state: AddProductTitleScreenState,
    interactor: AddProductTitleScreenInteractor
) {
    val selectedCategory = retain { mutableStateOf<Category?>(null) }
    val properties = rememberSaveable { mutableStateListOf<PropertyModel<*>?>() }
    val scrollState = rememberScrollState()
    val menuVisible = rememberSaveable { mutableStateOf(false) }
    val name = rememberSaveable { mutableStateOf("") }
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val salePrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val propertiesValid = properties.all { it != null } && properties.size == (selectedCategory.value?.template?.fields?.size ?: 0)

    Scaffold(
        modifier = modifier,
        title = stringResource(R.string.add_product),
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        enabled = name.value.isNotEmpty() &&
                                price.value != null &&
                                salePrice.value != null && propertiesValid &&
                                selectedCategory.value != null,
                        onClick = {
                            selectedCategory.value?.let { category ->
                                interactor.addProductTitle(
                                    properties = properties,
                                    name = name.value,
                                    price = price.value,
                                    salePrice = salePrice.value,
                                    description = description.value,
                                    category = category,
                                )
                            }
                        }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    ) { paddingValues ->
        val modifier = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is AddProductTitleScreenState.Loading -> LoadingScreen(modifier)

            is AddProductTitleScreenState.Success -> {
                if (selectedCategory.value == null) {
                    selectedCategory.value = state.categories.find { it.id == categoryId } ?: state.categories.firstOrNull()
                }

                val currentCategory = selectedCategory.value
                if (currentCategory != null) {
                    val categories = state.categories
                    val fields = currentCategory.template.fields
                    val innerModifier = Modifier.fillMaxWidth()

                    Column(modifier = modifier) {
                        Column(
                            modifier = innerModifier
                                .weight(1f)
                                .verticalScroll(scrollState)
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                modifier = innerModifier,
                                value = name.value,
                                onValueChange = { name.value = it },
                                label = { Text(text = stringResource(R.string.name)) },
                                shape = androidx.compose.material3.MaterialTheme.shapes.medium
                            )

                            VerticalSpacer(8)
                            OutlinedExposedDropDownTextField(
                                modifier = innerModifier,
                                text = currentCategory.name,
                                label = stringResource(R.string.category),
                                menuExpanded = menuVisible.value,
                                onExpandChange = { menuVisible.value = it },
                                onDismissRequested = { menuVisible.value = false },
                                items = categories,
                                itemText = { it.name },
                                onItemSelected = {
                                    selectedCategory.value = it
                                    properties.clear()
                                }
                            )

                            VerticalSpacer(8)
                            PropertiesCard(
                                modifier = innerModifier,
                                fields = fields,
                                properties = properties
                            )

                            VerticalSpacer(8)
                            OutlinedIntegerTextField(
                                modifier = innerModifier,
                                value = price.value,
                                onValueChange = { price.value = it },
                                label = stringResource(R.string.price)
                            )

                            VerticalSpacer(8)
                            OutlinedIntegerTextField(
                                modifier = innerModifier,
                                value = salePrice.value,
                                onValueChange = { salePrice.value = it },
                                label = stringResource(R.string.sale_price)
                            )

                            VerticalSpacer(8)
                            OutlinedCommentTextField(
                                modifier = innerModifier,
                                value = description.value,
                                onValueChange = { description.value = it }
                            )
                            VerticalSpacer(8)
                        }
                    }
                }
            }

            is AddProductTitleScreenState.Failure -> FailureScreen(
                modifier = modifier,
                retry = interactor::initialize
            )
        }
    }
}

@Preview
@Composable
private fun AddProductTitleScreenPreview() {
    MyFinancesTheme {
        AddProductTitleScreen(
            modifier = Modifier.fillMaxSize(),
            categoryId = category1.id,
            state = AddProductTitleScreenState.Success(categories),
            interactor = AddProductTitleScreenInteractor.dummy
        )
    }
}