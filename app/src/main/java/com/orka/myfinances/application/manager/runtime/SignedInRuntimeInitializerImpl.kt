package com.orka.myfinances.application.manager.runtime

import com.orka.myfinances.MainActivity
import com.orka.myfinances.R
import com.orka.myfinances.application.Logger
import com.orka.myfinances.application.factories.HttpLogger
import com.orka.myfinances.application.factories.httpClient
import com.orka.myfinances.application.manager.navigation.NavigationManager
import com.orka.myfinances.application.printer.BluetoothPrinter
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.format.Formatter
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.runtime.SignedInRuntimeInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SignedInRuntimeInitializerImpl(
    private val mainActivity: MainActivity,
    private val formatter: Formatter,
    private val logger: Logger
) : SignedInRuntimeInitializer {
    private var factory: Factory? = null
    private var navigator: NavigationManager? = null

    override fun initialize(session: Session, manager: SessionManager) {
        val logger = HttpLogger(this.logger)
        val httpClient = httpClient(logger, session.credentials, manager::logout)
        val navigator = NavigationManager(this.logger).apply { navigator = this }
        val printer = BluetoothPrinter(
            mainActivity = mainActivity,
            formatPrice = formatter,
            formatDecimal = formatter,
            scope = CoroutineScope(Dispatchers.Default)
        )

        factory = Factory(
            session = session,
            httpClient = httpClient,
            printer = printer,
            logger = this.logger,
            navigator = navigator,
            formatter = formatter,
            sessionManager = manager,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
        )
    }

    fun factory(): Factory {
        val factory = this.factory
        if(factory == null)
            throw Exception()
        else {
            this.factory = null
            return factory
        }
    }

    fun navigator(): NavigationManager {
        val navigator = this.navigator
        if(navigator == null)
            throw Exception()
        else {
            this.navigator = null
            return navigator
        }
    }
}