package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.remote.FolderRepository
import com.orka.myfinances.data.repositories.IdGenerator
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.data.repositories.debt.DebtRepository
import com.orka.myfinances.data.repositories.folder.CategoryRepository
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.data.repositories.office.OfficeApi
import com.orka.myfinances.data.repositories.office.OfficeRepository
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleRepository
import com.orka.myfinances.data.repositories.receive.ReceiveRepository
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.printer.pos.BluetoothPrinterImpl
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.home.viewmodel.profile.InfoRepository
import io.ktor.client.HttpClient

fun factory(
    printer: BluetoothPrinterImpl,
    logger: Logger,
    office: Office, httpClient: HttpClient,
    navigator: Navigator
): Factory {
    val idGenerator = IdGenerator()
    val templateRepository = TemplateRepository(idGenerator, httpClient)
    val folderRepository = FolderRepository(FolderApi(httpClient), office, templateRepository)
    val categoryRepository = CategoryRepository(folderRepository)
    val productTitleRepository = ProductTitleRepository(httpClient)
    val clientRepository = ClientRepository()
    val productRepository = ProductRepository(productTitleRepository, idGenerator)
    val stockRepository = StockRepository(office, productRepository, idGenerator)
    val basketRepository = BasketRepository(productRepository)
    val saleRepository = SaleRepository(
        generator = idGenerator,
        getProductById = productRepository,
        getClientById = clientRepository
    )
    val receiveRepository = ReceiveRepository(
        productTitleRepository = productTitleRepository,
        productRepository = productRepository,
        stockRepository = stockRepository,
        setProductTitlePrice = productTitleRepository,
        generator = idGenerator
    )
    val orderRepository = OrderRepository(
        generator = idGenerator,
        getProductById = productRepository,
        getClientById = clientRepository
    )
    val debtRepository = DebtRepository()
    val notificationRepository = NotificationRepository()
    val formatter = Formatter()
    val printer = printer
    val officeRepository = OfficeRepository(OfficeApi(httpClient), office.company)
    val infoRepository = InfoRepository(httpClient)

    return Factory(
        productTitleRepository = productTitleRepository,
        folderRepository = folderRepository,
        templateRepository = templateRepository,
        categoryRepository = categoryRepository,
        stockRepository = stockRepository,
        basketRepository = basketRepository,
        clientRepository = clientRepository,
        saleRepository = saleRepository,
        orderRepository = orderRepository,
        receiveRepository = receiveRepository,
        debtRepository = debtRepository,
        notificationRepository = notificationRepository,
        logger = logger,
        navigator = navigator,
        formatter = formatter,
        printer = printer,
        officeRepository = officeRepository,
        infoRepository = infoRepository
    )
}