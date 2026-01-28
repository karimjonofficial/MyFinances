package com.orka.myfinances.application

import android.util.Log
import com.orka.myfinances.core.Logger

class LoggerImpl : Logger {
    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}