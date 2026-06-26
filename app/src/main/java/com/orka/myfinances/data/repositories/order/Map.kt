package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.api.order.models.request.AddOrderApiRequest
import com.orka.myfinances.data.api.order.models.response.OrderApiModel
import com.orka.myfinances.data.api.order.models.response.OrderItemApiModel
import com.orka.myfinances.data.api.order.models.response.OrderProductApiModel
import com.orka.myfinances.data.api.user.UserApiModel
import com.orka.myfinances.data.dtos.office.OfficeDto
import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.dtos.order.OrderItemDto
import com.orka.myfinances.data.dtos.order.OrderProductDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.client.toDto
import com.orka.myfinances.data.repositories.debt.toDto
import com.orka.myfinances.data.repositories.toItem
import kotlin.time.Instant

fun Basket.toOrderRequest(
    clientId: Id,
    endDateTime: Instant? = null
): AddOrderRequest {
    return AddOrderRequest(
        clientId = clientId,
        items = items.map { it.toItem() },
        price = price,
        endDateTime = endDateTime,
        description = description
    )
}

fun AddOrderRequest.toApiRequest(officeId: Id): AddOrderApiRequest {
    return AddOrderApiRequest(
        clientId = clientId.value,
        branchId = officeId.value,
        price = price,
        description = description,
        endDateTime = endDateTime,
        items = items
    )
}

fun OrderApiModel.toDto(): OrderDto {
    return OrderDto(
        id = id,
        user = user.toDto(),
        client = client.toDto(),
        branch = branch.toDto(),
        price = price,
        endDateTime = endDateTime,
        completed = completed,
        completedDateTime = completedDateTime,
        description = description,
        items = items.map { it.toDto() },
        createdAt = createdAt
    )
}

fun OrderItemApiModel.toDto(): OrderItemDto {
    return OrderItemDto(
        id = id,
        amount = amount,
        product = product.toDto()
    )
}

fun OrderProductApiModel.toDto(): OrderProductDto {
    return OrderProductDto(
        id = id,
        name = title.name,
        price = price,
        salePrice = salePrice
    )
}

fun OfficeApiModel.toDto(): OfficeDto {
    return OfficeDto(
        id = id,
        companyId = companyId,
        name = name,
        address = address,
        phone = phone
    )
}
