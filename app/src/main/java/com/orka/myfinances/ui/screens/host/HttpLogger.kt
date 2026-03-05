package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.core.Logger

class HttpLogger(private val logger: Logger) : io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        logger.log(tag = "Ktor.HttpClient", message = message)
    }
}