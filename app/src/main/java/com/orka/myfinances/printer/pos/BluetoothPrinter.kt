package com.orka.myfinances.printer.pos

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log.d
import androidx.core.content.ContextCompat
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.printer.Printer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BluetoothPrinter(
    private val context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Printer {
    private val tag = "BluetoothPrinter"

    init {
        checkConnection()
    }

    private fun hasBluetoothPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val permission = Manifest.permission.BLUETOOTH_CONNECT
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    @SuppressLint("MissingPermission")
    private fun checkConnection() {
        if (!hasBluetoothPermission()) return
        scope.launch {
            try {
                BluetoothPrintersConnections.selectFirstPaired()
            } catch (e: Exception) {
                d(tag, e.message.toString())
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun print(sale: SaleApiModel) {
        if (!hasBluetoothPermission()) return
        scope.launch {
            try {
                val connection = BluetoothPrintersConnections.selectFirstPaired()
                if (connection != null) {
                    val printer = printer(connection)
                    val formattedText = """
                        [C]<b>My Finances</b>
                        [C]Sale ID: ${sale.id}
                        ${sale.items.joinToString("\n") { "[L]${it.product.title.name} [R]${it.amount}" }}
                        [R]TOTAL: [R]<b>${sale.price} UZS</b>
                        [C]Thank you!
                    """.trimIndent()
                    printer.useEscAsteriskCommand(true)
                    printer.printFormattedText(formattedText)
                } else d(tag, "Permissions are not granted")
            } catch (e: Exception) {
                d(tag, e.message.toString())
            }
        }
    }

    private fun printer(connection: BluetoothConnection): EscPosPrinter {
        val dpi = 203
        val width = 48f
        val charactersPerLine = 32

        return EscPosPrinter(
            connection,
            dpi,
            width,
            charactersPerLine
        )
    }
}
