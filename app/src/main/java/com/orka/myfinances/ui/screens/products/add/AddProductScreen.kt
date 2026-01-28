package com.orka.myfinances.ui.screens.products.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.folder.CategoryRepository
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.ProductTitleRepository
import com.orka.myfinances.data.repositories.product.models.PropertyModel
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductScreenState
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun AddProductScreen(
    modifier: Modifier = Modifier,
    category: Category,
    state: AddProductScreenState,
    viewModel: AddProductScreenViewModel,
    navigator: Navigator
) {
    Scaffold(
        modifier = modifier,
        title = stringResource(R.string.add_product)
    ) { paddingValues ->
        val m = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is AddProductScreenState.Loading -> LoadingScreen(m)

            is AddProductScreenState.Success -> {
                val categories = state.categories
                val scrollState = rememberScrollState()
                val menuVisible = rememberSaveable { mutableStateOf(false) }
                val name = rememberSaveable { mutableStateOf("") }
                val price = rememberSaveable { mutableIntStateOf(0) }
                val salePrice = rememberSaveable { mutableIntStateOf(0) }
                val description = rememberSaveable { mutableStateOf("") }
                val selectedCategoryId = retain { mutableStateOf(category.id) }
                val selectedCategory = categories.find { it.id == selectedCategoryId.value }!!
                val fields = selectedCategory.template.fields
                val properties = rememberSaveable { mutableStateListOf<PropertyModel<*>?>() }.apply {
                    repeat(fields.size) { add(null) }
                }

                Column(modifier = m) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text(text = stringResource(R.string.name)) }
                        )

                        OutlinedExposedDropDownTextField(
                            text = selectedCategory.name,
                            label = stringResource(R.string.category),
                            menuExpanded = menuVisible.value,
                            onExpandChange = { menuVisible.value = it },
                            onDismissRequested = { menuVisible.value = false },
                            items = categories,
                            itemText = { it.name },
                            onItemSelected = { selectedCategoryId.value = it.id }
                        )

                        if (fields.isNotEmpty()) {
                            VerticalSpacer(8)
                            Text(text = stringResource(R.string.properties))

                            VerticalSpacer(4)
                            PropertiesList(
                                fields = fields,
                                onSuccess = { index, model -> properties[index] = model }
                            )

                            VerticalSpacer(8)
                        }

                        OutlinedTextField(
                            value = price.intValue.toString(),
                            onValueChange = { price.intValue = it.toInt() },
                            label = { Text(text = stringResource(R.string.price)) }
                        )

                        OutlinedTextField(
                            value = salePrice.intValue.toString(),
                            onValueChange = { salePrice.intValue = it.toInt() },
                            label = { Text(text = stringResource(R.string.sale_price)) }
                        )

                        OutlinedTextField(
                            value = description.value,
                            minLines = 3,
                            onValueChange = { description.value = it },
                            label = { Text(text = stringResource(R.string.description)) }
                        )
                    }

                    BottomAppBar {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    viewModel.addProduct(
                                        titleId = id1,
                                        properties = properties,
                                        name = name.value,
                                        price = price.intValue,
                                        salePrice = salePrice.intValue,
                                        description = description.value,
                                        category = selectedCategory,
                                    )
                                    navigator.back()
                                }
                            ) {
                                Text(text = stringResource(R.string.save))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddProductScreenPreview() {
    val viewModel = viewModel {
        AddProductScreenViewModel(
            productRepository = ProductRepository(ProductTitleRepository()),
            categoryRepository = CategoryRepository(FolderRepository(TemplateRepository())),
            logger = DummyLogger()
        )
    }
    viewModel.initialize()
    val state = viewModel.uiState.collectAsState()

    MyFinancesTheme {
        AddProductScreen(
            modifier = Modifier.fillMaxSize(),
            category = category1,
            state = state.value,
            viewModel = viewModel,
            navigator = DummyNavigator()
        )
    }
}