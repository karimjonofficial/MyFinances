package com.orka.myfinances

import android.app.Application
import com.orka.myfinances.fixtures.ApiProviderImpl
import com.orka.myfinances.fixtures.LocalSessionStorageImpl
import com.orka.myfinances.lib.LoggerImpl
import com.orka.myfinances.ui.managers.UiManager
import kotlinx.coroutines.Dispatchers

class MyFinancesApplication : Application() {
    private val logger = LoggerImpl()
    private val storage = LocalSessionStorageImpl()
    private val provider = ApiProviderImpl()
    val manager = UiManager(logger, storage, provider, Dispatchers.Default)
}