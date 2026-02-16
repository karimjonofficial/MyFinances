package com.orka.myfinances.ui.screens.product

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.CommentTextField
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.IntegerTextField

@Composable
fun ReceiveDialog(
    modifier: Modifier = Modifier.Companion,
    dismissRequest: () -> Unit,
    onSuccess: (Int, Int, Int, Int, String?) -> Unit,
) {
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val salePrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val amount = rememberSaveable { mutableStateOf<Int?>(null) }
    val totalPrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val comment = rememberSaveable { mutableStateOf<String?>(null) }

    Dialog(
        modifier = modifier,
        title = stringResource(R.string.receive),
        supportingText = stringResource(R.string.fill_the_lines_below_to_receive),
        dismissRequest = dismissRequest,
        onSuccess = {
            val p = price.value
            val sp = salePrice.value
            val a = amount.value
            val t = totalPrice.value
            val c = comment.value

            if (p != null && sp != null && a != null && t != null) {
                onSuccess(p, sp, a, t, c)
            }
        }
    ) {
        IntegerTextField(
            value = price.value,
            onValueChange = { price.value = it },
            label = stringResource(R.string.price)
        )

        IntegerTextField(
            value = salePrice.value,
            onValueChange = { salePrice.value = it },
            label = stringResource(R.string.sale_price)
        )

        IntegerTextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            label = stringResource(R.string.amount)
        )

        IntegerTextField(
            value = totalPrice.value,
            onValueChange = { totalPrice.value = it },
            label = stringResource(R.string.total_price)
        )

        CommentTextField(
            value = comment.value,
            onValueChange = { comment.value = it }
        )
    }
}