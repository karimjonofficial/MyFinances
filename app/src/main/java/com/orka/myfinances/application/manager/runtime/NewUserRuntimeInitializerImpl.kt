package com.orka.myfinances.application.manager.runtime

import com.orka.myfinances.R
import com.orka.myfinances.application.Logger
import com.orka.myfinances.application.factories.httpClient
import com.orka.myfinances.application.factories.httpLogger
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.runtime.NewUserRuntimeInitializer
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.host.SelectBranchScreenViewModelFactory

class NewUserRuntimeInitializerImpl(private val logger: Logger) : NewUserRuntimeInitializer {
    private var factory: SelectBranchScreenViewModelFactory? = null

    override fun initialize(credentials: Credentials, manager: SessionManager) {
        val logger = httpLogger(this.logger)
        val httpClient = httpClient(logger, credentials, manager::logout)

        factory = SelectBranchScreenViewModelFactory(
            httpClient = httpClient,
            sessionManager = manager,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
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