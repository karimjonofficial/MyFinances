package com.orka.myfinances.application.factories

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.printer.pos.BluetoothPrinterImpl
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient

fun factory(
    session: Session,
    printer: BluetoothPrinterImpl,
    logger: Logger,
    httpClient: HttpClient,
    navigator: Navigator
): Factory {
    val formatter = Formatter()
    val printer = printer

    return Factory(
        logger = logger,
        navigator = navigator,
        formatter = formatter,
        printer = printer,
        session = session,
        client = httpClient,
        basketRepository = BasketRepository(httpClient)
    )
}

fun httpLogger(logger: Logger): HttpLogger {
    return HttpLogger(logger)
}