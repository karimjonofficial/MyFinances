package com.orka.myfinances.application.manager

import com.orka.myfinances.lib.logger.Logger

class DummyLogger : Logger {
    override fun log(tag: String, message: String) {}
}
