package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.zipped.CompanyModel
import com.orka.myfinances.data.zipped.CompanyOfficeModel
import com.orka.myfinances.data.zipped.SessionModel
import com.orka.myfinances.data.zipped.UserModel

fun Session.toModel(): SessionModel {
    return SessionModel(
        user = this.user.toModel(),
        credential = this.credential,
        companyOffice = this.companyOffice.toModel(),
        company = this.companyOffice.company.toModel()
    )
}

fun makeSession(
    credential: Credential,
    userModel: UserModel,
    companyOfficeModel: CompanyOfficeModel,
    companyModel: CompanyModel
): Session {
    val company = Company(
        id = companyModel.id.toId(),
        name = companyModel.name,
        address = companyModel.address,
        phone = companyModel.phone
    )
    val companyOffice = CompanyOffice(
        id = companyOfficeModel.id.toId(),
        name = companyOfficeModel.name,
        templates = companyOfficeModel.templates,
        address = companyOfficeModel.address,
        phone = companyOfficeModel.phone,
        company = company
    )
    val user = User(
        id = userModel.id.toId(),
        firstName = userModel.firstName,
        userName = userModel.userName,
        company = company,
        lastName = userModel.lastName,
        patronymic = userModel.patronymic,
    )

    return Session(user, credential, companyOffice)
}