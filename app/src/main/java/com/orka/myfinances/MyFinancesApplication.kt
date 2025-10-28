package com.orka.myfinances

import android.app.Application
import com.orka.myfinances.fixtures.data.storages.LocalSessionStorageImpl
import com.orka.myfinances.fixtures.factories.ApiProviderImpl
import com.orka.myfinances.lib.LoggerImpl
import com.orka.myfinances.ui.managers.ui.UiManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyFinancesApplication : Application() {
    private val logger = LoggerImpl()
    private val storage = LocalSessionStorageImpl()
    private val provider = ApiProviderImpl()
    val manager = UiManager(logger, storage, provider, CoroutineScope(Dispatchers.Main + SupervisorJob()))
}