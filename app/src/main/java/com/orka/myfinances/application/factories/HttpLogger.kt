package com.orka.myfinances.application.factories

import com.orka.myfinances.lib.logger.Logger

typealias HLogger = io.ktor.client.plugins.logging.Logger

class HttpLogger(private val logger: Logger) : HLogger {
    override fun log(message: String) {
        logger.log(tag = "Ktor.HttpClient", message = message)
    }
}