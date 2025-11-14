package com.orka.myfinances.testLib

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.product.models.AddProductRequest

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

val id1 = Id(1)
val id2 = Id(2)

val credential = Credential(
    token = token,
    refresh = refresh
)

val company = Company(
    id = id1,
    name = name,
    address = address,
    phone = phone
)

val companyOffice = CompanyOffice(
    id = id1,
    name = name,
    company = company,
    templates = emptyList(),
    address = address,
    phone = phone
)

val user = User(
    id = id1,
    firstName = firstName,
    userName = username,
    company = company,
    lastName = lastName,
    patronymic = patronymic,
    phone = phone,
    address = address
)

val session = Session(
    credential = credential,
    companyOffice = companyOffice,
    user = user
)

val textTemplateField = TemplateField(
    id = id1,
    name = name,
    type = text
)
val intTemplateField = TemplateField(
    id = id1,
    name = name,
    type = int
)
val templateFields = listOf(
    textTemplateField,
    intTemplateField
)

val template = Template(id1, name, templateFields)
val addProductRequest = AddProductRequest(
    name = name,
    warehouseId = 1,
    price = price,
    salePrice = salePrice,
    properties = emptyList(),
    description = description
)

val warehouse1 = Warehouse(
    id = id1,
    name = name,
    template = template
)
val warehouse2 = Warehouse(
    id = id2,
    name = name,
    template = template
)

val product1 = Product(
    id = id1,
    name = name,
    price = price,
    salePrice = salePrice,
    warehouse = warehouse1,
    properties = emptyList(),
    description = description
)
val product2 = Product(
    id = id2,
    name = name,
    price = price,
    salePrice = salePrice,
    warehouse = warehouse1,
    properties = emptyList(),
    description = description
)
val products = listOf(product1, product2)

val catalog1 = Catalog(
    id = id1,
    name = name
)
val catalog2 = Catalog(
    id = id2,
    name = name
)