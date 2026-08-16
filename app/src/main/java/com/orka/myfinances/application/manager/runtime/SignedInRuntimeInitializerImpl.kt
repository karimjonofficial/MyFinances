package com.orka.myfinances.application.manager.runtime

import com.orka.myfinances.application.Logger
import com.orka.myfinances.application.adapters.PrintersDataSource
import com.orka.myfinances.application.factories.HttpLogger
import com.orka.myfinances.application.factories.httpClient
import com.orka.myfinances.application.manager.navigation.NavigationManager
import com.orka.myfinances.data.database.AppDatabase
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.runtime.SignedInRuntimeInitializer

class SignedInRuntimeInitializerImpl(
    private val database: AppDatabase,
    private val printersDataSource: PrintersDataSource,
    private val logger: Logger
) : SignedInRuntimeInitializer {
    private var factory: Factory? = null
    private var navigator: NavigationManager? = null

    override fun initialize(session: Session, manager: SessionManager) {
        val logger = HttpLogger(this.logger)
        val httpClient = httpClient(logger, session.credentials, manager::logout)
        val navigator = NavigationManager(this.logger).apply { this@SignedInRuntimeInitializerImpl.navigator = this }

        factory = Factory(
            session = session,
            httpClient = httpClient,
            database = database,
            logger = this.logger,
            navigator = navigator,
            printersDataSource = printersDataSource,
            sessionManager = manager
        )
    }

    fun factory(): Factory {
        val factory = this.factory
        if(factory == null)
            throw Exception()
        else return factory
    }

    fun navigator(): NavigationManager {
        val navigator = this.navigator
        if(navigator == null)
            throw Exception()
        else return navigator
    }
}