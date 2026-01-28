package com.orka.myfinances.application

import android.app.Application
import com.orka.myfinances.fixtures.data.storages.LocalSessionStorageImpl
import com.orka.myfinances.ui.screens.host.UiManager

class MyFinancesApplication : Application() {
    fun manager(): UiManager {
        val logger = LoggerImpl()
        val storage = LocalSessionStorageImpl()
        val provider = ApiProviderImpl()
        return UiManager(logger, storage, provider)
    }
}