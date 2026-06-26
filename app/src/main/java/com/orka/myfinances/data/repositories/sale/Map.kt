package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.data.api.sale.models.request.AddSaleApiRequest
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.api.sale.models.response.SaleItemApiModel
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.dtos.sale.SaleItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.client.toDto
import com.orka.myfinances.data.repositories.debt.toDto
import com.orka.myfinances.lib.data.models.Item

fun SaleApiModel.toDto(): SaleDto {
    return SaleDto(
        id = id,
        client = client.toDto(),
        user = user.toDto(),
        items = items.map { it.toDto() },
        dateTime = dateTime,
        price = price,
        description = description
    )
}

fun SaleItemApiModel.toDto(): SaleItemDto {
    return SaleItemDto(
        id = id,
        productName = product.title.name,
        amount = amount
    )
}

fun AddSaleRequest.toApiRequest(officeId: Id): AddSaleApiRequest {
    return AddSaleApiRequest(
        clientId = clientId.value,
        items = items,
        price = price,
        officeId = officeId.value,
        description = description
    )
}

fun Basket.toSaleRequest(clientId: Id): AddSaleRequest {
    return AddSaleRequest(
        clientId = clientId,
        items = items.map { it.toSaleItemRequest() },
        price = price,
        description = description
    )
}

fun BasketItem.toSaleItemRequest(): Item {
    return Item(
        id = product.id,
        amount = amount
    )
}