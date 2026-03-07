package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.core.Logger

typealias HLogger = io.ktor.client.plugins.logging.Logger

class HttpLogger(private val logger: Logger) : HLogger {
    override fun log(message: String) {
        logger.log(tag = "Ktor.HttpClient", message = message)
    }
}