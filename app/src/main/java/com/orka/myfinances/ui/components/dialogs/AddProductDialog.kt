package com.orka.myfinances.ui.components.dialogs

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.textfield.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.spacer.VerticalSpacer

@Composable
fun AddProductDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSuccess: (String, Int, Int, Int, Int) -> Unit
) {
    val name = rememberSaveable { mutableStateOf<String?>(null) }
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val salePrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val exposedPrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val amount = rememberSaveable { mutableStateOf<Int?>(null) }

    Dialog(
        modifier = modifier,
        dismissRequest = onDismissRequest,
        title = stringResource(R.string.add_product),
        supportingText = stringResource(R.string.fill_the_lines_below_to_add_a_product),
        onSuccess = {
            val nameVal = name.value
            val priceVal = price.value
            val salePriceVal = salePrice.value
            val exposedPriceVal = exposedPrice.value
            val amountVal = amount.value
            if(
                !nameVal.isNullOrBlank() &&
                priceVal != null &&
                salePriceVal != null &&
                exposedPriceVal != null &&
                amountVal != null
            )
            onSuccess(nameVal, priceVal, salePriceVal, exposedPriceVal, amountVal)
        }
    ) {
        OutlinedTextField(
            value = name.value ?: "",
            onValueChange = { name.value = it },
            label = { Text(text = stringResource(R.string.name)) }
        )

        VerticalSpacer(4)
        OutlinedIntegerTextField(
            value = price.value,
            onValueChange = { price.value = it },
            label = stringResource(R.string.price)
        )

        VerticalSpacer(4)
        OutlinedIntegerTextField(
            value = salePrice.value,
            onValueChange = { salePrice.value = it },
            label = stringResource(R.string.sale_price)
        )

        VerticalSpacer(4)
        OutlinedIntegerTextField(
            value = exposedPrice.value,
            onValueChange = { exposedPrice.value = it },
            label = stringResource(R.string.exposed_price)
        )

        VerticalSpacer(4)
        OutlinedIntegerTextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            label = stringResource(R.string.amount)
        )
    }
}
