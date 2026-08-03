package com.orka.myfinances.data.repositories.printer

import com.orka.myfinances.data.models.printer.PrinterModel
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.Insert

interface PrinterRepository : Get<PrinterModel>, Insert<AddPrinterRequest>