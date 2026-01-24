package com.orka.myfinances.ui.screens.templates.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateFieldModel
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.add.components.TemplateFieldCard
import com.orka.myfinances.ui.theme.MyFinancesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTemplateScreen(
    modifier: Modifier = Modifier,
    types: List<String>,
    viewModel: AddTemplateScreenViewModel,
    navigator: Navigator
) {
    val name = rememberSaveable { mutableStateOf("") }
    val fields = rememberSaveable { mutableStateListOf<TemplateFieldModel>() }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.templates)) }
            )
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            if (name.value.isNotBlank() && fields.isNotEmpty()) {
                                val template = AddTemplateRequest(name.value, fields)
                                viewModel.addTemplate(template)
                                navigator.back()
                            }
                        }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    ) { paddingValues ->
        val showDialog = rememberSaveable { mutableStateOf(false) }

        if (showDialog.value) {
            AddTemplateFieldDialog(
                types = types,
                dismissRequest = { showDialog.value = false },
                onSuccess = { name, typeId ->
                    fields.add(TemplateFieldModel(name, typeId))
                }
            )
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(text = stringResource(R.string.name_of_template)) }
                )

                VerticalSpacer(16)
                Text(text = stringResource(R.string.properties_of_template))
                VerticalSpacer(8)

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(items = fields) { field ->

                        TemplateFieldCard(
                            modifier = Modifier.size(120.dp),
                            field = field,
                            type = types[field.typeId],
                            onRemove = { fields.remove(field) }
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier.size(120.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            IconButton(
                                modifier = Modifier.size(64.dp),
                                onClick = { showDialog.value = true }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.add),
                                    contentDescription = stringResource(R.string.add)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TemplateScreenPreview() {
    val types = listOf("text", "number", "range")
    val repository = TemplateRepository()
    val addTemplateScreenViewModel = viewModel { AddTemplateScreenViewModel(repository) }
    val navigationManager = DummyNavigator()

    MyFinancesTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.templates)) }
                )
            }
        ) { innerPadding ->
            AddTemplateScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                types = types,
                viewModel = addTemplateScreenViewModel,
                navigator = navigationManager
            )
        }
    }
}