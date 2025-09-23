package com.orka.myfinances.ui.screens.add.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateFieldModel
import com.orka.myfinances.fixtures.DummyNavigationManager
import com.orka.myfinances.fixtures.data.repositories.TemplateRepositoryImpl
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.add.template.components.TemplateFieldCard
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun AddTemplateScreen(
    modifier: Modifier = Modifier,
    types: List<String>,
    viewModel: AddTemplateScreenViewModel,
    navigationManager: NavigationManager
) {
    val name = rememberSaveable { mutableStateOf("") }
    val names = rememberSaveable { mutableStateListOf("") }
    val fieldTypes = rememberSaveable { mutableStateListOf<Int?>(null) }
    val count = rememberSaveable { mutableIntStateOf(1) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {

            val countValue = count.intValue

            if (countValue > names.size) {
                repeat(countValue - names.size) {
                    names.add("")
                    fieldTypes.add(null)
                }
            } else if (countValue < names.size) {
                repeat(names.size - countValue) {
                    names.removeAt(names.lastIndex)
                    fieldTypes.removeAt(fieldTypes.lastIndex)
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text(text = stringResource(R.string.name_of_template)) }
            )

            VerticalSpacer(16)
            Text(text = stringResource(R.string.properties_of_template))
            VerticalSpacer(8)

            repeat(countValue) { index ->
                val typeIndex = fieldTypes[index]
                val name = names[index]

                TemplateFieldCard(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    index = index + 1,
                    name = name,
                    type = if (typeIndex == null) "" else types[typeIndex],
                    onNameChange = { names[index] = it },
                    onTypeIndexChange = { fieldTypes[index] = it },
                    types = types,
                    onRemove = {
                        names.removeAt(index)
                        fieldTypes.removeAt(index)
                        count.intValue = names.size
                    }
                )

                VerticalSpacer(4)
            }

            VerticalSpacer(8)
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { count.intValue++ }
            ) {
                Text(text = stringResource(R.string.add))
            }
        }

        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (name.value.isNotBlank() && names.all { it.isNotBlank() } && fieldTypes.all { it != null }) {
                            val fields = names.mapIndexed { i, name ->
                                TemplateFieldModel(
                                    name,
                                    fieldTypes[i]!!
                                )
                            }
                            val template = AddTemplateRequest(name.value, fields)
                            viewModel.addTemplate(template)
                            navigationManager.back()
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.save))
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
    val repository = TemplateRepositoryImpl()
    val addTemplateScreenViewModel = AddTemplateScreenViewModel(repository)
    val navigationManager = DummyNavigationManager()

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
                navigationManager = navigationManager
            )
        }
    }
}