package com.orka.myfinances.application.factories

import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient

fun factory(
    session: Session,
    printer: Printer,
    logger: Logger,
    httpClient: HttpClient,
    navigator: Navigator,
    sessionManager: SessionManager,
    formatter: Formatter,
    loading: UiText,
    failure: UiText
): Factory {
    return Factory(
        logger = logger,
        navigator = navigator,
        formatter = formatter,
        printer = printer,
        session = session,
        httpClient = httpClient,
        sessionManager = sessionManager,
        loading = loading,
        failure = failure
    )
}

fun httpLogger(logger: Logger): HttpLogger {
    return HttpLogger(logger)
}