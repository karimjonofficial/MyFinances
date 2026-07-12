package com.orka.myfinances.testFixtures.logger

import com.orka.myfinances.logger.Logger

class DummyLogger : Logger {
    override fun log(tag: String, message: String) {}
}