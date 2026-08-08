package com.orka.myfinances.data.repositories.printer

import com.orka.myfinances.data.dtos.printer.PrinterDto
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.Insert

interface PrinterRepository : Get<PrinterDto>, Insert<AddPrinterRequest>, GetById<PrinterDto>