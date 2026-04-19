package com.orka.myfinances.ui.screens.product.add

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel

@Composable
fun ProductTitleEditorScreen(
    modifier: Modifier = Modifier,
    title: String,
    state: State<AddProductTitleScreenModel>,
    onRetry: () -> Unit,
    onSave: (List<PropertyModel<*>?>, String, Int?, Int?, String?, Id) -> Unit
) {
    StatefulScreen(
        modifier = modifier,
        title = title,
        state = state,
        onRetry = onRetry,
    ) { innerModifier, model ->
        ProductTitleEditorContent(
            modifier = innerModifier,
            model = model,
            onSave = onSave
        )
    }
}

