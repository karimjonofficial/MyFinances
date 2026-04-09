package com.orka.myfinances.application

import android.app.Application
import androidx.room.Room
import com.orka.myfinances.R
import com.orka.myfinances.application.manager.UiManager
import com.orka.myfinances.data.storages.room.AppDatabase
import com.orka.myfinances.data.storages.room.LocalSessionStorageImpl
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.printer.pos.BluetoothPrinterImpl
import kotlinx.coroutines.CoroutineScope

class MyFinancesApplication : Application() {
    private val database by lazy {
        Room
            .databaseBuilder(
                context = applicationContext,
                klass = AppDatabase::class.java,
                name = "my-finances-db"
            )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    fun manager(printer: (CoroutineScope) -> BluetoothPrinterImpl): UiManager {
        val loading =  UiText.Res(R.string.loading)
        val failure = UiText.Res(R.string.failure)
        val logger = Logger()
        val storage = LocalSessionStorageImpl(database.sessionDao())
        return UiManager(storage, printer, loading, failure, logger)
    }
}