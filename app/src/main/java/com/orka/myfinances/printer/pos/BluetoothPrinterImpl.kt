package com.orka.myfinances.printer.pos

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.orka.myfinances.data.api.sale.SaleApiModel
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.printer.PrinterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BluetoothPrinterImpl(
    private val context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Printer {

    private val _state = MutableStateFlow<PrinterState>(PrinterState.Disconnected)
    val state: StateFlow<PrinterState> = _state.asStateFlow()

    init {
        checkConnection()
    }

    private fun hasBluetoothPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkConnection() {
        if (!hasBluetoothPermission()) {
            _state.value = PrinterState.Disconnected
            return
        }
        scope.launch {
            try {
                val connection = BluetoothPrintersConnections.selectFirstPaired()
                if (connection != null) {
                    _state.value = PrinterState.Connected
                } else {
                    _state.value = PrinterState.Disconnected
                }
            } catch (_: Exception) {
                _state.value = PrinterState.Disconnected
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun print(sale: SaleApiModel) {
        if (!hasBluetoothPermission()) {
            return
        }
        scope.launch {
            try {
                val connection = BluetoothPrintersConnections.selectFirstPaired()
                if (connection != null) {
                    val printer = EscPosPrinter(connection, 203, 48f, 32)
                    val formattedText = """
                        [C]<b>My Finances</b>
                        [C]Sale ID: ${sale.id}
                        [L]--------------------------------
                        ${sale.items.joinToString("\n") { "[L]${it.product.title.name} [R]${it.amount}" }}
                        [L]--------------------------------
                        [R]TOTAL: [R]<b>${sale.price} UZS</b>
                        [C]Thank you!
                    """.trimIndent()
                    printer.printFormattedText(formattedText)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
