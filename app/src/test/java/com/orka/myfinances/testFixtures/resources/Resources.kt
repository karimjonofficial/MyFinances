package com.orka.myfinances.testFixtures.resources

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.fixtures.resources.models.product.productTitle1
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

val successfulAddProductRequest = AddProductRequest(
    titleId = productTitle1.id,
    name = name,
    price = price,
    salePrice = salePrice,
    properties = emptyList(),
    description = description
)

val failingAddProductRequest = AddProductRequest(
    titleId = Id(10),
    name = name,
    price = price,
    salePrice = salePrice,
    properties = emptyList(),
    description = description
)