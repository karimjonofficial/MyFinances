package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.User
import com.orka.myfinances.data.zipped.CompanyModel
import com.orka.myfinances.data.zipped.OfficeModel
import com.orka.myfinances.data.zipped.SessionModel
import com.orka.myfinances.data.zipped.UserModel

fun Session.toModel(): SessionModel {
    return SessionModel(
        user = this.user.toModel(),
        credential = this.credential,
        companyOffice = this.office.toModel(),
        company = this.office.company.toModel()
    )
}

fun makeSession(
    credential: Credential,
    userModel: UserModel,
    officeModel: OfficeModel,
    companyModel: CompanyModel
): Session {
    val company = Company(
        id = companyModel.id.toId(),
        name = companyModel.name,
        address = companyModel.address,
        phone = companyModel.phone
    )
    val office = Office(
        id = officeModel.id.toId(),
        name = officeModel.name,
        address = officeModel.address,
        phone = officeModel.phone,
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

    return Session(user, credential, office)
}