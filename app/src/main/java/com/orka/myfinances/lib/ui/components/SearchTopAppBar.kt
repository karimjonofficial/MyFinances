package com.orka.myfinances.lib.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onSearch: (String) -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val searchMode = rememberSaveable { mutableStateOf(false) }
    val searchText = rememberSaveable { mutableStateOf("") }

    val closeSearch = {
        searchMode.value = false
        searchText.value = ""
        onSearch("")
    }

    if (searchMode.value) {
        BackHandler(onBack = closeSearch)
    }

    LaunchedEffect(searchMode.value) {
        if (searchMode.value) {
            snapshotFlow { searchText.value }
                .drop(1)
                .debounce(300.milliseconds)
                .collect { onSearch(it) }
        }
    }

    if (searchMode.value) {
        TopAppBar(
            modifier = modifier,
            title = {
                TextField(
                    value = searchText.value,
                    onValueChange = { searchText.value = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = stringResource(R.string.search)
                        )
                    },
                    placeholder = { Text(text = stringResource(R.string.search)) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            navigationIcon = {
                IconButton(onClick = closeSearch) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null
                    )
                }
            }
        )
    } else {
        TopAppBar(
            modifier = modifier,
            title = { Text(text = title) },
            actions = {
                IconButton(onClick = { searchMode.value = true }) {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = null
                    )
                }
                actions()
            }
        )
    }
}