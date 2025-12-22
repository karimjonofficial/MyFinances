package com.orka.myfinances.fixtures.core

import com.orka.myfinances.core.Logger

class DummyLogger : Logger {
    override fun log(tag: String, message: String) {}
}