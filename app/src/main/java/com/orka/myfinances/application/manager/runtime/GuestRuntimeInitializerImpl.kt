package com.orka.myfinances.application.manager.runtime

import com.orka.myfinances.R
import com.orka.myfinances.application.Logger
import com.orka.myfinances.application.factories.HttpLogger
import com.orka.myfinances.application.factories.httpClient
import com.orka.myfinances.data.api.auth.AuthenticationApi
import com.orka.myfinances.data.repositories.auth.AuthenticationRepository
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.runtime.GuestRuntimeInitializer
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.factories.LoginScreenViewModelFactory

class GuestRuntimeInitializerImpl(private val logger: Logger) : GuestRuntimeInitializer {
    private var factory: LoginScreenViewModelFactory? = null

    override fun initialize(manager: SessionManager) {
        val logger = HttpLogger(this.logger)
        val authenticationApi = AuthenticationApi(httpClient(logger))
        val authenticator = AuthenticationRepository(authenticationApi)
        factory = LoginScreenViewModelFactory(
            authenticator = authenticator,
            sessionManager = manager,
            loading = UiText.Res(R.string.loading),
            logger = this.logger
        )
    }

    fun factory(): LoginScreenViewModelFactory {
        val factory = this.factory
        if(factory == null)
            throw Exception()
        else {
            this.factory = null
            return factory
        }
    }
}