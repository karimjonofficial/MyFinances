package com.orka.myfinances.application

import android.app.Application
import androidx.room.Room
import com.orka.myfinances.MainActivity
import com.orka.myfinances.R
import com.orka.myfinances.application.factories.Formatter
import com.orka.myfinances.application.manager.UiManager
import com.orka.myfinances.data.storages.room.AppDatabase
import com.orka.myfinances.data.storages.room.LocalSessionStorageImpl
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.printer.pos.BluetoothPrinter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import net.posprinter.POSConnect

class MyFinancesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        POSConnect.init(this)
    }

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

    fun manager(mainActivity: MainActivity): UiManager {
        val formatter = Formatter()
        val printer = BluetoothPrinter(
            mainActivity = mainActivity,
            formatPrice = formatter,
            formatDecimal = formatter,
            scope = CoroutineScope(Dispatchers.Default)
        )
        val loading =  UiText.Res(R.string.loading)
        val failure = UiText.Res(R.string.failure)
        val logger = Logger()
        val storage = LocalSessionStorageImpl(database.sessionDao())
        return UiManager(storage, printer, loading, failure, formatter, logger)
    }
}