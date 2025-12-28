package com.orka.myfinances.testFixtures.resources

import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import kotlin.time.Instant

const val name = "Name"
const val firstName = "FirstName"
const val lastName = "LastName"
const val username = "username"
const val password = "password"
const val patronymic = "Patronymic"
const val token = "token"
const val refresh = "refresh"
const val viewModel = "viewModel"
const val description = "description"
const val int = "int"
const val text = "text"
const val address = "Address"
const val phone = "Phone"
const val price = 100
const val salePrice = 110
const val amount = 1

val dateTime = Instant.parse("2024-01-01T12:00:00Z")

val addProductRequest = AddProductRequest(
    name = name,
    warehouseId = 1,
    price = price,
    salePrice = salePrice,
    properties = emptyList(),
    description = description
)