package com.orka.myfinances.application

import android.util.Log
import com.orka.myfinances.lib.logger.Logger

class Logger : Logger {
    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}