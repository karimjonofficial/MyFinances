package com.orka.myfinances

import android.app.Application
import com.orka.myfinances.fixtures.data.storages.LocalSessionStorageImpl
import com.orka.myfinances.fixtures.factories.ApiProviderImpl
import com.orka.myfinances.impl.ui.managers.UiManager
import com.orka.myfinances.lib.LoggerImpl

class MyFinancesApplication : Application() {
    private val logger = LoggerImpl()
    private val storage = LocalSessionStorageImpl()
    private val provider = ApiProviderImpl()
    val manager = UiManager(logger, storage, provider)
}