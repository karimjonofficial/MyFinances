package com.orka.myfinances.ui.screens.products.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.folder.CategoryRepository
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleRepository
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.data.repositories.template.field.TemplateFieldRepository
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductTitleScreenState
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductTitleScreenViewModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun AddProductTitleScreen(
    modifier: Modifier = Modifier,
    category: Category,
    state: AddProductTitleScreenState,
    viewModel: AddProductTitleScreenViewModel,
    navigator: Navigator
) {
    Scaffold(
        modifier = modifier,
        title = stringResource(R.string.add_product)
    ) { paddingValues ->
        val m = Modifier.scaffoldPadding(paddingValues)

        when (state) {
            is AddProductTitleScreenState.Loading -> LoadingScreen(m)

            is AddProductTitleScreenState.Success -> {
                val categories = state.categories
                val scrollState = rememberScrollState()
                val menuVisible = rememberSaveable { mutableStateOf(false) }
                val name = rememberSaveable { mutableStateOf("") }
                val price = rememberSaveable { mutableStateOf<Int?>(0) }
                val salePrice = rememberSaveable { mutableStateOf<Int?>(0) }
                val description = rememberSaveable { mutableStateOf("") }
                val selectedCategoryId = retain { mutableStateOf(category.id) }
                val selectedCategory = categories.find { it.id == selectedCategoryId.value }!!
                val fields = selectedCategory.template.fields

                val properties =
                    rememberSaveable { mutableStateListOf<PropertyModel<*>?>() }.apply {
                        repeat(fields.size) { add(null) }
                    }

                Column(modifier = m) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(scrollState)
                            .padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text(text = stringResource(R.string.name)) }
                        )

                        OutlinedExposedDropDownTextField(
                            modifier = Modifier.fillMaxWidth(),
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
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                HorizontalDivider(modifier = Modifier.weight(1f))
                                HorizontalSpacer(4)
                                Text(text = stringResource(R.string.properties))
                                HorizontalSpacer(4)
                                HorizontalDivider(modifier = Modifier.weight(1f))
                            }
                            VerticalSpacer(8)

                            VerticalSpacer(4)
                            PropertiesList(
                                fields = fields,
                                onSuccess = { index, model -> properties[index] = model }
                            )

                            VerticalSpacer(8)
                        }

                        HorizontalDivider()
                        VerticalSpacer(8)

                        OutlinedIntegerTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = price.value,
                            onValueChange = { price.value = it },
                            label = { Text(text = stringResource(R.string.price)) }
                        )

                        OutlinedIntegerTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = salePrice.value,
                            onValueChange = { salePrice.value = it },
                            label = { Text(text = stringResource(R.string.sale_price)) }
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
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
                                    viewModel.addProductTitle(
                                        properties = properties,
                                        name = name.value,
                                        price = price.value,
                                        salePrice = salePrice.value,
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
private fun AddProductTitleScreenPreview() {
    val viewModel = viewModel {
        AddProductTitleScreenViewModel(
            productTitleRepository = ProductTitleRepository(
                categoryRepository = CategoryRepository(FolderRepository(TemplateRepository())),
                fieldRepository = TemplateFieldRepository()
            ),
            categoryRepository = CategoryRepository(FolderRepository(TemplateRepository())),
            logger = DummyLogger()
        )
    }
    viewModel.initialize()
    val state = viewModel.uiState.collectAsState()

    MyFinancesTheme {
        AddProductTitleScreen(
            modifier = Modifier.fillMaxSize(),
            category = category1,
            state = state.value,
            viewModel = viewModel,
            navigator = DummyNavigator()
        )
    }
}