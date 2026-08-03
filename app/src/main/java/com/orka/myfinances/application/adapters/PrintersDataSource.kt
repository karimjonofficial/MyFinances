package com.orka.myfinances.application.adapters

import android.bluetooth.BluetoothAdapter
import com.orka.myfinances.data.models.printer.PrinterModel
import com.orka.myfinances.lib.data.repositories.Get

class PrintersDataSource(private val adapter: BluetoothAdapter) : Get<PrinterModel> {
    override suspend fun getAll(): List<PrinterModel>? {
        try {
            val devices = adapter.bondedDevices
            return devices.map { PrinterModel(it.name, it.address) }
        } catch (_: SecurityException) {
            return null
        }
    }
}