package com.orka.myfinances.data.zipped

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.CompanyOffice
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.User
import com.orka.myfinances.lib.extensions.toId

data class SessionModel(
    val user: UserModel,
    val credential: Credential,
    val companyOffice: CompanyOfficeModel,
    val company: CompanyModel
) {
    fun toSession(): Session {
        val company = Company(
            id = this.company.id.toId(),
            name = this.company.name,
            address = this.company.address,
            phone = this.company.phone
        )
        val user = User(
            id = this.user.id.toId(),
            firstName = this.user.firstName,
            userName = this.user.userName,
            company = company,
            lastName = this.user.lastName,
            patronymic = this.user.patronymic,
            phone = this.user.phone,
            address = this.user.address,
        )
        val companyOffice = CompanyOffice(
            id = this.companyOffice.id.toId(),
            name = this.companyOffice.name,
            company = company,
            templates = this.companyOffice.templates,
            address = this.companyOffice.address,
            phone = this.companyOffice.phone
        )

        return Session(
            user = user,
            credential = credential,
            companyOffice = companyOffice
        )
    }
}