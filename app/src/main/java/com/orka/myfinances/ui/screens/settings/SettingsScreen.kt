package com.orka.myfinances.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.components.SectionTitle
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: State<SettingsScreenModel>,
    interactor: SettingsScreenInteractor
) {
    Scaffold(
        modifier = modifier,
        title = stringResource(R.string.settings)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            item {
                VerticalSpacer(12)
                SectionTitle(text = stringResource(R.string.defaults))
            }

            item {
                VerticalSpacer(4)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { interactor.toSelectDefaultCategory() }
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.empty_category),
                        fontWeight = FontWeight.Medium
                    )

                    val title = state.value?.defaultCategory
                    val color = if(state !is State.Loading) {
                        if(title == null) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    } else MaterialTheme.colorScheme.primary

                    Text(
                        text = if (state is State.Loading) stringResource(R.string.loading)
                        else title ?: stringResource(R.string.default_category_is_not_set_yet),
                        color = color
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    MyFinancesTheme {
        SettingsScreen(
            state = State.Success(
                value = SettingsScreenModel(category1.name)
            ),
            interactor = SettingsScreenInteractor.dummy
        )
    }
}

interface SettingsScreenInteractor {
    fun toSelectDefaultCategory()

    companion object {
        val dummy = object : SettingsScreenInteractor {
            override fun toSelectDefaultCategory() {}
        }
    }
}