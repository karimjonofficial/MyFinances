package com.orka.myfinances.fixtures

import com.orka.myfinances.core.Logger

class DummyLogger : Logger {
    override fun log(tag: String, message: String) {}
}