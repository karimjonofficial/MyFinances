package com.orka.myfinances.printer.pos

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.printer.PrinterStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.posprinter.IDeviceConnection
import net.posprinter.POSConnect
import net.posprinter.POSConst
import net.posprinter.POSPrinter

class BluetoothPrinter(
    private val mainActivity: Activity,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Printer {
    private val tag = "BluetoothPrinter"
    private var curConnect: IDeviceConnection? = null

    private val _status = MutableStateFlow<PrinterStatus>(PrinterStatus.Disconnected)
    override val status: StateFlow<PrinterStatus> = _status.asStateFlow()

    init {
        getPermissions()
    }

    private fun getPermissions() {
        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            permissions.add(Manifest.permission.BLUETOOTH)
            permissions.add(Manifest.permission.BLUETOOTH_ADMIN)
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        val toRequest = permissions.filter {
            ContextCompat.checkSelfPermission(mainActivity, it) != PackageManager.PERMISSION_GRANTED
        }

        if (toRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(mainActivity, toRequest.toTypedArray(), 100)
        }
    }

    override fun connect() {
        if ((_status.value is PrinterStatus.Connected) || (_status.value is PrinterStatus.Connecting)) return
        _status.value = PrinterStatus.Connecting
        connectFirstPaired { success ->
            if (!success) {
                _status.value = PrinterStatus.Error("Failed to connect")
            }
        }
    }

    override fun disconnect() {
        curConnect?.close()
        curConnect = null
        _status.value = PrinterStatus.Disconnected
    }

    private fun connectFirstPaired(onResult: (Boolean) -> Unit) {
        val bluetoothManager = mainActivity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter

        try {
            val pairedDevices = bluetoothAdapter.bondedDevices
            if (pairedDevices != null && pairedDevices.isNotEmpty()) {
                val firstDevice = pairedDevices.first()
                Log.d(tag, "Attempting to connect to ${firstDevice.name} (${firstDevice.address})")

                val connection = POSConnect.createDevice(POSConnect.DEVICE_TYPE_BLUETOOTH)
                connection.connect(firstDevice.address) { status, _, _ ->
                    if (status == POSConnect.CONNECT_SUCCESS) {
                        curConnect = connection
                        _status.value = PrinterStatus.Connected(firstDevice.name ?: firstDevice.address)
                        onResult(true)
                    } else {
                        _status.value = PrinterStatus.Error("Connection failed with status: $status")
                        onResult(false)
                    }
                }
            } else {
                Log.e(tag, "No paired devices found")
                _status.value = PrinterStatus.Error("No paired devices found")
                onResult(false)
            }
        } catch (e: SecurityException) {
            Log.e(tag, "Bluetooth permission not granted", e)
            _status.value = PrinterStatus.Error("Bluetooth permission not granted")
            onResult(false)
        }
    }

    override fun print(sale: SaleApiModel) {
        scope.launch {
            if (curConnect == null) {
                _status.value = PrinterStatus.Connecting
                connectFirstPaired { success ->
                    if (success) {
                        executePrint(sale)
                    } else {
                        Log.e(tag, "Could not connect to any paired printer")
                    }
                }
            } else {
                executePrint(sale)
            }
        }
    }

    private fun executePrint(sale: SaleApiModel) {
        val connection = curConnect ?: return
        try {
            val printer = POSPrinter(connection)
            printer.initializePrinter()
                .printText(
                    "SALE RECEIPT\n",
                    POSConst.ALIGNMENT_CENTER,
                    POSConst.FNT_BOLD,
                    POSConst.TXT_1WIDTH or POSConst.TXT_2HEIGHT
                )
                .feedLine(1)
                .printText("Sale ID: ${sale.id}\n", POSConst.ALIGNMENT_LEFT, POSConst.FNT_DEFAULT, POSConst.TXT_1WIDTH)
                .printText("Client: ${sale.client.firstName} ${sale.client.lastName ?: ""}\n", POSConst.ALIGNMENT_LEFT, POSConst.FNT_DEFAULT, POSConst.TXT_1WIDTH)
                .printText("--------------------------------\n", POSConst.ALIGNMENT_LEFT, POSConst.FNT_DEFAULT, POSConst.TXT_1WIDTH)

            sale.items.forEach { item ->
                printer.printText(
                    "${item.product.title.name} x ${item.amount}\n",
                    POSConst.ALIGNMENT_LEFT,
                    POSConst.FNT_DEFAULT,
                    POSConst.TXT_1WIDTH
                )
            }

            printer.printText("--------------------------------\n", POSConst.ALIGNMENT_LEFT, POSConst.FNT_DEFAULT, POSConst.TXT_1WIDTH)
                .printText("TOTAL: ${sale.price}\n", POSConst.ALIGNMENT_RIGHT, POSConst.FNT_BOLD, POSConst.TXT_2WIDTH or POSConst.TXT_1HEIGHT)
                .feedLine(3)
                .cutHalfAndFeed(1)

            Log.d(tag, "Print command sent successfully")
        } catch (e: Exception) {
            Log.e(tag, "Printing failed", e)
            _status.value = PrinterStatus.Error("Printing failed: ${e.message}")
            disconnect()
        }
    }
}
