package com.orka.myfinances.application.manager.runtime

import com.orka.myfinances.application.Logger
import com.orka.myfinances.application.factories.HttpLogger
import com.orka.myfinances.application.factories.httpClient
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.factories.SelectBranchScreenViewModelFactory
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.runtime.NewUserRuntimeInitializer

class NewUserRuntimeInitializerImpl(private val logger: Logger) : NewUserRuntimeInitializer {
    private var factory: SelectBranchScreenViewModelFactory? = null

    override fun initialize(credentials: Credentials, manager: SessionManager) {
        val logger = HttpLogger(this.logger)
        val httpClient = httpClient(logger, credentials, manager::logout)

        factory = SelectBranchScreenViewModelFactory(
            httpClient = httpClient,
            sessionManager = manager,
            logger = this.logger
        )
    }

    fun factory(): SelectBranchScreenViewModelFactory {
        val factory = this.factory
        if(factory == null)
            throw Exception()
        else {
            this.factory = null
            return factory
        }
    }
}