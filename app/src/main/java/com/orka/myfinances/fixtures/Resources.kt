package com.orka.myfinances.fixtures

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User
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