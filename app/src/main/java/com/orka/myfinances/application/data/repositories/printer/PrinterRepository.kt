package com.orka.myfinances.application.data.repositories.printer

import com.orka.myfinances.data.database.daos.PrinterDao
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.dtos.printer.PrinterDto
import com.orka.myfinances.data.repositories.printer.AddPrinterRequest
import com.orka.myfinances.data.repositories.printer.PrinterEvent
import com.orka.myfinances.data.repositories.printer.PrinterRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PrinterRepository(private val dao: PrinterDao) : PrinterRepository {
    private val events = MutableSharedFlow<PrinterEvent>()
    val flow = events.asSharedFlow()

    override suspend fun getAll(): List<PrinterDto> {
        return dao.getAllPrinters().map { it.toModel() }
    }

    override suspend fun insert(request: AddPrinterRequest): Boolean {
        return if(dao.getAllPrinters().none { it.address == request.address }) {
            dao.addPrinter(request.name, request.address)
            events.emit(PrinterEvent)
            true
        } else false
    }

    override suspend fun getById(id: Id): PrinterDto? {
        return dao.getPrinterById(id.value)?.toModel()
    }
}