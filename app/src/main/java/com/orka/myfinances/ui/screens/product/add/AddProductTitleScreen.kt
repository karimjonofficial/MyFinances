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
import androidx.compose.material3.TextField
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
import com.orka.myfinances.lib.ui.components.CommentTextField
import com.orka.myfinances.lib.ui.components.ExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.IntegerTextField
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun AddProductTitleScreen(
    modifier: Modifier = Modifier,
    categoryId: Id,
    state: State<AddProductTitleScreenModel>,
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
            is State.Loading -> LoadingScreen(modifier)
            is State.Failure -> FailureScreen(
                modifier = modifier,
                retry = interactor::refresh
            )

            is State.Success -> {
                val model = state.value
                if (selectedCategory.value == null) {
                    selectedCategory.value = model.categories.find { it.id == categoryId } ?: model.categories.firstOrNull()
                }

                val currentCategory = selectedCategory.value
                if (currentCategory != null) {
                    val categories = model.categories
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
                            TextField(
                                modifier = innerModifier,
                                value = name.value,
                                onValueChange = { name.value = it },
                                label = { Text(text = stringResource(R.string.name)) }
                            )

                            VerticalSpacer(8)
                            ExposedDropDownTextField(
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
                            IntegerTextField(
                                modifier = innerModifier,
                                value = price.value,
                                onValueChange = { price.value = it },
                                label = { Text(text = stringResource(R.string.price)) }
                            )

                            VerticalSpacer(8)
                            IntegerTextField(
                                modifier = innerModifier,
                                value = salePrice.value,
                                onValueChange = { salePrice.value = it },
                                label = { Text(text = stringResource(R.string.sale_price)) }
                            )

                            VerticalSpacer(8)
                            CommentTextField(
                                modifier = innerModifier,
                                value = description.value,
                                onValueChange = { description.value = it },
                                label = stringResource(R.string.description)
                            )
                            VerticalSpacer(8)
                        }
                    }
                }
            }
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
            state = State.Success(AddProductTitleScreenModel(categories)),
            interactor = AddProductTitleScreenInteractor.dummy
        )
    }
}