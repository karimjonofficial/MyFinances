package com.orka.myfinances.ui.screens.product.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.Dialog
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun ReceiveDialog(
    modifier: Modifier = Modifier,
    price: Int,
    dismissRequest: () -> Unit,
    onSuccess: (Int, Int, String?) -> Unit,
) {
    val amount = rememberSaveable { mutableStateOf<Int?>(null) }
    val totalPrice = rememberSaveable { mutableStateOf<Int?>(null) }
    val comment = rememberSaveable { mutableStateOf<String?>(null) }

    Dialog(
        modifier = modifier,
        title = stringResource(R.string.receive),
        supportingText = stringResource(R.string.fill_the_lines_below_to_receive),
        dismissRequest = dismissRequest,
        onSuccess = {
            val a = amount.value
            val t = totalPrice.value
            val c = comment.value

            if (a != null && t != null) {
                onSuccess(a, t, c)
            }
        }
    ) {
        OutlinedIntegerTextField(
            value = amount.value,
            onValueChange = {
                amount.value = it
                if(it != null)
                    totalPrice.value = it * price
                else totalPrice.value = null
            },
            label = stringResource(R.string.amount)
        )

        VerticalSpacer(4)
        OutlinedIntegerTextField(
            value = totalPrice.value,
            onValueChange = { totalPrice.value = it },
            label = stringResource(R.string.total_price)
        )

        VerticalSpacer(4)
        OutlinedCommentTextField(
            value = comment.value,
            onValueChange = { comment.value = it }
        )
    }
}