package com.orka.myfinances.application.printer

import com.orka.myfinances.application.data.repositories.printer.PrinterRepository
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.repositories.defaults.GetDefaultPrinter
import com.orka.myfinances.data.repositories.printer.AddPrinterRequest
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.printer.PrinterModel
import com.orka.myfinances.printer.PrinterStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.posprinter.IDeviceConnection
import net.posprinter.POSConnect
import net.posprinter.POSConnect.createDevice
import net.posprinter.POSConst
import net.posprinter.POSPrinter
import kotlin.time.Duration.Companion.seconds

class PrinterManager(
    private val logger: Logger,
    private val repository: PrinterRepository,
    private val getDefaultPrinter: GetDefaultPrinter,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : Printer {
    private val tag = "BluetoothPrinter"
    private var curConnect: IDeviceConnection? = null

    private val _status = MutableStateFlow<PrinterStatus>(PrinterStatus.Disconnected)
    val status = _status.asStateFlow()

    init {
        connectToDefault()
    }

    fun initialize() {
        scope.launch {
            setState(PrinterStatus.Connecting)
            delay(1.seconds)
        }
    }

    override fun connect(model: PrinterModel) {
        scope.launch {
            setState(PrinterStatus.Connecting)
            val request = AddPrinterRequest(model.name, model.address)
            repository.insert(request)
            tryToConnect(model)
        }
    }

    override fun connectToDefault() {
        scope.launch {
            val id = getDefaultPrinter.getDefaultPrinter()
            if(id != null) {
                val dto = repository.getById(id)
                if (dto != null) {
                    connect(dto.model)
                }
            }
        }
    }

    override fun disconnect() {
        curConnect?.close()
        curConnect = null
        setState(PrinterStatus.Disconnected)
    }

    private fun setState(state: PrinterStatus) {
        logger.log(tag, "Setting state to $state")
        _status.value = state
    }

    override fun printSaleReceipt(sale: SaleDto) {
        scope.launch {
            try {
                val connection = curConnect
                if (connection != null) {
                    val printer = POSPrinter(connection)
                    printer.initializePrinter()
                        .printText(
                            "SALE RECEIPT\n",
                            POSConst.ALIGNMENT_CENTER,
                            POSConst.FNT_BOLD,
                            POSConst.TXT_1WIDTH or POSConst.TXT_2HEIGHT
                        )
                        .feedLine(1)
                        .printText(
                            "Sale ID: ${sale.id}\n",
                            POSConst.ALIGNMENT_LEFT,
                            POSConst.FNT_DEFAULT,
                            POSConst.TXT_1WIDTH
                        )
                        .printText(
                            "Client: ${sale.client.firstName} ${sale.client.lastName ?: ""}\n",
                            POSConst.ALIGNMENT_LEFT,
                            POSConst.FNT_DEFAULT,
                            POSConst.TXT_1WIDTH
                        )
                        .printText(
                            "--------------------------------\n",
                            POSConst.ALIGNMENT_LEFT,
                            POSConst.FNT_DEFAULT,
                            POSConst.TXT_1WIDTH
                        )

                    sale.items.forEach { item ->
                        printer.printText(
                            "${item.productName} x ${item.amount}\n",
                            POSConst.ALIGNMENT_LEFT,
                            POSConst.FNT_DEFAULT,
                            POSConst.TXT_1WIDTH
                        )
                    }

                    printer.printText(
                        "--------------------------------\n",
                        POSConst.ALIGNMENT_LEFT,
                        POSConst.FNT_DEFAULT,
                        POSConst.TXT_1WIDTH
                    )
                        .printText(
                            "TOTAL: ${sale.price} UZS\n",
                            POSConst.ALIGNMENT_RIGHT,
                            POSConst.FNT_BOLD,
                            POSConst.TXT_2WIDTH or POSConst.TXT_1HEIGHT
                        )
                        .feedLine(3)
                        .cutHalfAndFeed(1)

                    logger.log(tag, "Print command sent successfully")
                }
            } catch (e: Exception) {
                logger.log(tag, "Printing failed: ${e.message}")
                setState(PrinterStatus.Error("Printing failed: ${e.message}"))
                disconnect()
            }
        }
    }

    private fun tryToConnect(model: PrinterModel) {
        try {
            val connection = createDevice(POSConnect.DEVICE_TYPE_BLUETOOTH)

            connection.connect(model.address) { status, address, message ->
                when (status) {
                    POSConnect.CONNECT_SUCCESS -> {
                        curConnect = connection
                        setState(PrinterStatus.Connected(model))
                    }

                    POSConnect.CONNECT_FAIL -> {
                        curConnect = null
                        setState(
                            PrinterStatus.Error(
                                message.ifBlank { "Failed to connect to $address." }
                            )
                        )
                    }

                    POSConnect.CONNECT_INTERRUPT -> {
                        curConnect = null
                        setState(PrinterStatus.Disconnected)
                    }

                    POSConnect.BLUETOOTH_INTERRUPT -> {
                        curConnect = null
                        setState(PrinterStatus.Disconnected)
                    }

                    POSConnect.SEND_FAIL -> {
                        setState(
                            PrinterStatus.Error(
                                message.ifBlank { "Failed to send data to printer." }
                            )
                        )
                    }

                    POSConnect.USB_ATTACHED -> {
                        logger.log(tag, "USB device attached: $address")
                    }

                    POSConnect.USB_DETACHED -> {
                        curConnect = null
                        setState(PrinterStatus.Disconnected)
                    }

                    else -> {
                        logger.log(
                            tag,
                            "Unknown printer status=$status, address=$address, message=$message"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            setState(PrinterStatus.Error("Connection failed with exception: $e"))
        }
    }
}