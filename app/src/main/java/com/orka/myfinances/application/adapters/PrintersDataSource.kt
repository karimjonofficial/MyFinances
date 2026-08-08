package com.orka.myfinances.application.adapters

import android.bluetooth.BluetoothAdapter
import com.orka.myfinances.data.sources.PrinterDataSource
import com.orka.myfinances.printer.PrinterModel

class PrintersDataSource(private val adapter: BluetoothAdapter) : PrinterDataSource {
    override suspend fun getAll(): List<PrinterModel>? {
        try {
            val devices = adapter.bondedDevices
            return devices.map { PrinterModel(it.name, it.address) }
        } catch (_: SecurityException) {
            return null
        }
    }
}