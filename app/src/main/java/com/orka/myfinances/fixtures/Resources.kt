package com.orka.myfinances.fixtures

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Product
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.data.models.template.FieldType
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField

const val standardDelayDurationInMillis = 1000L

val id = Id(1)
val credential = Credential(
    token = "token",
    refresh = "refresh"
)
val company = Company(
    id = id,
    name = "Name",
    address = "Address",
    phone = "1234567890"
)
val companyOffice = CompanyOffice(
    id = id,
    name = "Name",
    company = company,
    templates = emptyList(),
    address = "Address",
    phone = "1234567890"
)
val user = User(
    id = id,
    firstName = "User",
    userName = "admin",
    company = company,
    lastName = "LastName",
    patronymic = "Patronymic",
    phone = "1234567890",
    address = "Address"
)
val templates = listOf(
    Template(
        id = id,
        name = "Test 1",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),
    Template(
        id = id,
        name = "Test 2",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),
    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),

    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),

    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),
    Template(
        id = id,
        name = "Test 1",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),
    Template(
        id = id,
        name = "Test 2",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),
    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),

    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),

    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),
    Template(
        id = id,
        name = "Test 1",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),
    Template(
        id = id,
        name = "Test 2",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),
    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),

    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    ),

    Template(
        id = id,
        name = "Test 3",
        fields = listOf(
            TemplateField(
                id = id,
                name = "test",
                type = FieldType.Text
            )
        )
    )
)
val folders = listOf(
    Catalog(
        id = Id(1),
        name = "Electronics",
        folders = listOf(
            ProductFolder(
                id = Id(101),
                name = "Smartphones",
                template = templates[0],
                products = listOf(
                    Product(Id(9001), templates[0]),
                    Product(Id(9002), templates[0])
                )
            ),
            ProductFolder(
                id = Id(102),
                name = "Laptops",
                template = templates[1],
                products = listOf(
                    Product(Id(9003), templates[1]),
                    Product(Id(9004), templates[1])
                )
            )
        )
    ),
    Catalog(
        id = Id(2),
        name = "Home Appliances",
        folders = listOf(
            ProductFolder(
                id = Id(201),
                name = "Refrigerators",
                template = templates[2],
                products = listOf(
                    Product(Id(9101), templates[2]),
                    Product(Id(9102), templates[2])
                )
            )
        )
    ),
    ProductFolder(
        id = Id(300),
        name = "Standalone Gadgets",
        template = templates[3],
        products = listOf(
            Product(Id(9201), templates[3]),
            Product(Id(9202), templates[3])
        )
    )
)