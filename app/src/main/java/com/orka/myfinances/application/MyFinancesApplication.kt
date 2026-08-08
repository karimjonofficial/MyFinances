package com.orka.myfinances.application

import android.app.Application
import android.bluetooth.BluetoothManager
import androidx.core.content.getSystemService
import androidx.room.Room
import com.orka.myfinances.application.adapters.PrintersDataSource
import com.orka.myfinances.application.data.api.InfoApi
import com.orka.myfinances.application.data.repositories.InfoRepository
import com.orka.myfinances.application.data.storages.DefaultsStorageImpl
import com.orka.myfinances.application.data.storages.credentials.CredentialsStorageImpl
import com.orka.myfinances.application.factories.HttpLogger
import com.orka.myfinances.application.factories.httpClient
import com.orka.myfinances.application.manager.runtime.GuestRuntimeInitializerImpl
import com.orka.myfinances.application.manager.runtime.NewUserRuntimeInitializerImpl
import com.orka.myfinances.application.manager.runtime.SignedInRuntimeInitializerImpl
import com.orka.myfinances.application.manager.ui.UiManager
import com.orka.myfinances.application.validators.CredentialsValidatorImpl
import com.orka.myfinances.data.database.AppDatabase
import net.posprinter.POSConnect

class MyFinancesApplication : Application() {
    private val logger = Logger()
    private val httpLogger = HttpLogger(logger)
    private val httpClient = httpClient(httpLogger)
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
    lateinit var guestRuntimeInitializer: GuestRuntimeInitializerImpl
    lateinit var newUserRuntimeInitializer: NewUserRuntimeInitializerImpl
    lateinit var signedInRuntimeInitializer: SignedInRuntimeInitializerImpl
    //TODO remove these ugly hacks


    override fun onCreate() {
        super.onCreate()
        POSConnect.init(this)
    }

    fun manager(): UiManager {
        val bluetoothManager = getSystemService<BluetoothManager>()
        val adapter = bluetoothManager?.adapter
        val printersDataSource = PrintersDataSource(adapter!!)//TODO
        val credentialsStorage = CredentialsStorageImpl(database.credentialsDao())
        val defaultsStorage = DefaultsStorageImpl(database.defaultsDao())
        val credentialsValidator = CredentialsValidatorImpl(httpClient, credentialsStorage)
        val guestRuntimeInitializer = GuestRuntimeInitializerImpl(logger)
        this.guestRuntimeInitializer = guestRuntimeInitializer
        val newUserRuntimeInitializer = NewUserRuntimeInitializerImpl(logger)
        this.newUserRuntimeInitializer = newUserRuntimeInitializer
        val signedInRuntimeInitializer = SignedInRuntimeInitializerImpl(database, printersDataSource, logger)
        this.signedInRuntimeInitializer = signedInRuntimeInitializer
        val infoRepository = InfoRepository(InfoApi(httpClient))
        val manager =  UiManager(
            credentialsStorage = credentialsStorage,
            credentialsValidator = credentialsValidator,
            defaultsStorage = defaultsStorage,
            guestRuntimeInitializer = guestRuntimeInitializer,
            newUserRuntimeInitializer = newUserRuntimeInitializer,
            signedInRuntimeInitializer = signedInRuntimeInitializer,
            infoRepository = infoRepository,
            logger = logger
        )
        manager.initialize()
        return manager
    }
}