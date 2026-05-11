package com.orka.myfinances.ui.screens.receive.add

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R

@Composable
fun AddReceiveScreenBottomBar(
    interactor: AddReceiveScreenInteractor,
    productTitle: ProductTitleItemModel?,
    price: Int?,
    salePrice: Int?,
    exposedPrice: Int?,
    amount: Int?,
    totalPrice: Int?,
    description: String?
) {
    val saveable = productTitle != null && amount != null && amount > 0 &&
            price != null && price > 0 &&
            salePrice != null && salePrice > price &&
            exposedPrice != null && exposedPrice > salePrice &&
            totalPrice != null

    BottomAppBar(contentPadding = PaddingValues(horizontal = 8.dp)) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            enabled = saveable,
            onClick = {
                interactor.add(
                    title = productTitle,
                    price = price,
                    salePrice = salePrice,
                    exposedPrice = exposedPrice,
                    amount = amount,
                    totalPrice = totalPrice,
                    description = description
                )
            }
        ) {
            Text(text = stringResource(R.string.add))
        }
    }
}