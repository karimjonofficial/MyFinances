package com.orka.myfinances

import android.app.Application
import com.orka.myfinances.fixtures.SessionDataSourceImpl
import com.orka.myfinances.lib.AndroidLogger
import com.orka.myfinances.ui.managers.UiManager

class MyFinancesApplication : Application() {
    private val logger = AndroidLogger()
    private val sessionDataSource = SessionDataSourceImpl()
    val manager = UiManager(logger, sessionDataSource)
}