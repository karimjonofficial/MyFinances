package com.orka.myfinances

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.application.MyFinancesApplication
import com.orka.myfinances.printer.pos.BluetoothPrinterImpl
import com.orka.myfinances.ui.screens.host.HostScreen
import com.orka.myfinances.ui.theme.MyFinancesTheme

class MainActivity : ComponentActivity() {
    private val requestBluetoothPermissionLauncher = registerForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.entries.any { !it.value }) {
            throw Exception("Print permissions are not granted")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestBluetoothPermissionLauncher.launch(
                input = arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        }

        setContent {
            val manager = (application as MyFinancesApplication).manager(
                printer = {
                    BluetoothPrinterImpl(
                        context = this,
                        scope = it
                    )
                }
            )
            val uiState = manager.uiState.collectAsState()

            MyFinancesTheme {
                HostScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = uiState.value
                )
            }
        }
    }
}