package com.orka.myfinances.lib

import android.util.Log
import com.orka.myfinances.core.Logger

class AndroidLogger : Logger {
    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}