package com.orka.myfinances.testLib

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.folder.FolderType

const val name = "Name"

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
val session = Session(
    credential = credential,
    companyOffice = companyOffice,
    user = user
)
val textTemplateField = TemplateField(
    id = id,
    name = name,
    type = "int"
)
val intTemplateField = TemplateField(
    id = id,
    name = name,
    type = "string"
)
val templateFields = listOf(
    textTemplateField,
    intTemplateField
)
val template = Template(id, name, templateFields)
val catalogFolderType = FolderType.Catalog
val productFolderType = FolderType.ProductFolder(template.id.value)