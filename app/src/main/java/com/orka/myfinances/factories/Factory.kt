package com.orka.myfinances.factories

import com.orka.myfinances.R
import com.orka.myfinances.application.factories.Formatter
import com.orka.myfinances.application.viewmodels.catalog.CatalogScreenViewModel
import com.orka.myfinances.application.viewmodels.checkout.CheckoutScreenViewModel
import com.orka.myfinances.application.viewmodels.client.details.ClientScreenViewModel
import com.orka.myfinances.application.viewmodels.client.list.ClientsScreenViewModel
import com.orka.myfinances.application.viewmodels.debt.details.DebtScreenViewModel
import com.orka.myfinances.application.viewmodels.debt.list.DebtsScreenViewModel
import com.orka.myfinances.application.viewmodels.history.ReceiveContentViewModel
import com.orka.myfinances.application.viewmodels.history.SaleContentViewModel
import com.orka.myfinances.application.viewmodels.home.basket.BasketContentViewModel
import com.orka.myfinances.application.viewmodels.home.folder.FoldersContentViewModel
import com.orka.myfinances.application.viewmodels.home.profile.ProfileContentViewModel
import com.orka.myfinances.application.viewmodels.notifications.NotificationsScreenViewModel
import com.orka.myfinances.application.viewmodels.order.OrderScreenViewModel
import com.orka.myfinances.application.viewmodels.orders.OrdersScreenViewModel
import com.orka.myfinances.application.viewmodels.receive.add.AddReceiveScreenViewModel
import com.orka.myfinances.application.viewmodels.receive.details.ReceiveScreenViewModel
import com.orka.myfinances.application.viewmodels.sale.SaleScreenViewModel
import com.orka.myfinances.application.viewmodels.template.add.AddTemplateScreenViewModel
import com.orka.myfinances.application.viewmodels.template.details.TemplateScreenViewModel
import com.orka.myfinances.application.viewmodels.template.list.TemplatesScreenViewModel
import com.orka.myfinances.application.viewmodels.title.add.AddProductTitleScreenViewModel
import com.orka.myfinances.application.viewmodels.title.details.ProductTitleScreenViewModel
import com.orka.myfinances.application.viewmodels.warehouse.WarehouseScreenViewModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.notification.NotificationApi
import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.user.UserApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.printer.pos.BluetoothPrinterImpl
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableSharedFlow

class Factory(
    private val session: Session,
    client: HttpClient,
    private val printer: BluetoothPrinterImpl,
    private val basketRepository: BasketRepository,
    private val logger: Logger,
    private val navigator: Navigator,
    private val formatter: Formatter
) {
    private val loading = UiText.Res(R.string.loading)
    private val failure = UiText.Res(R.string.failure)

    private val stockFlow = MutableSharedFlow<StockEvent>()
    private val templateFlow = MutableSharedFlow<TemplateEvent>()
    private val productTitleFlow = MutableSharedFlow<ProductTitleEvent>()
    private val folderFlow = MutableSharedFlow<FolderEvent>()
    private val saleFlow = MutableSharedFlow<SaleEvent>()
    private val receiveFlow = MutableSharedFlow<ReceiveEvent>()

    private val clientApi = ClientApi(client)
    private val folderApi = FolderApi(client)
    private val templateApi = TemplateApi(client)
    private val productTitleApi = ProductTitleApi(client)
    private val stockApi = StockApi(client, session.office)
    private val receiveApi = ReceiveApi(client, session.office)
    private val saleApi = SaleApi(client, session.office)
    private val orderApi = OrderApi(client, session.office)
    private val debtApi = DebtApi(client)
    private val notificationApi = NotificationApi(client)
    private val officeApi = OfficeApi(client)
    private val userApi = UserApi(client)

    fun foldersViewModel(): FoldersContentViewModel {
        return FoldersContentViewModel(
            folderApi = folderApi,
            templateApi = templateApi,
            navigator = navigator,
            office = session.office,
            logger = logger
        )
    }

    fun templatesViewModel(): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(
            templateApi = templateApi,
            navigator = navigator,
            logger = logger,
            events = templateFlow
        )
    }

    fun addTemplateViewModel(): AddTemplateScreenViewModel {
        return AddTemplateScreenViewModel(
            templateApi = templateApi,
            navigator = navigator
        )
    }

    fun addProductViewModel(): AddProductTitleScreenViewModel {
        return AddProductTitleScreenViewModel(
            office = session.office,
            folderApi = folderApi,
            productTitleApi = productTitleApi,
            navigator = navigator,
            logger = logger
        )
    }

    fun warehouseViewModel(category: Category): WarehouseScreenViewModel {
        return WarehouseScreenViewModel(
            category = category,
            productTitleApi = productTitleApi,
            stockApi = stockApi,
            basketRepository = basketRepository,
            productTitleEvents = productTitleFlow,
            navigator = navigator,
            formatPrice = formatter,
            formatDecimal = formatter,
            stockEvents = stockFlow,
            logger = logger
        )
    }

    fun catalogViewModel(catalog: Catalog): CatalogScreenViewModel {
        return CatalogScreenViewModel(
            office = session.office,
            catalog = catalog,
            folderApi = folderApi,
            templateApi = templateApi,
            events = folderFlow,
            navigator = navigator,
            logger = logger
        )
    }

    fun basketViewModel(): BasketContentViewModel {
        return BasketContentViewModel(
            repository = basketRepository,
            navigator = navigator,
            formatPrice = formatter,
            formatDecimal = formatter,
            logger = logger
        )
    }

    fun clientsViewModel(): ClientsScreenViewModel {
        return ClientsScreenViewModel(
            clientApi = clientApi,
            loading = loading,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }

    fun clientViewModel(id: Id): ClientScreenViewModel {
        return ClientScreenViewModel(
            id = id,
            clientApi = clientApi,
            navigator = navigator,
            logger = logger
        )
    }

    fun salesViewModel(): SaleContentViewModel {
        return SaleContentViewModel(
            loading = loading,
            failure = failure,
            saleApi = saleApi,
            events = saleFlow,
            navigator = navigator,
            priceFormatter = formatter,
            dateFormatter = formatter,
            formatDateTime = formatter,
            logger = logger,
        )
    }

    fun saleViewModel(id: Id): SaleScreenViewModel {
        return SaleScreenViewModel(
            id = id,
            saleApi = saleApi,
            formatPrice = formatter,
            formatDateTime = formatter,
            loading = loading,
            formatDecimal = formatter,
            navigator = navigator,
            logger = logger
        )
    }

    fun receivesViewModel(): ReceiveContentViewModel {
        return ReceiveContentViewModel(
            receiveApi = receiveApi,
            events = receiveFlow,
            loading = loading,
            failure = failure,
            navigator = navigator,
            formatPrice = formatter,
            formatDate = formatter,
            formatTime = formatter,
            formatDecimal = formatter,
            logger = logger
        )
    }

    fun checkoutViewModel(): CheckoutScreenViewModel {
        return CheckoutScreenViewModel(
            clientApi = clientApi,
            saleApi = saleApi,
            orderApi = orderApi,
            basketRepository = basketRepository,
            logger = logger,
            navigator = navigator,
            formatPrice = formatter,
            formatDecimal = formatter,
            printer = printer,
            printerState = printer.state
        )
    }

    fun addReceiveViewModel(id: Id): AddReceiveScreenViewModel {
        return AddReceiveScreenViewModel(
            id = id,
            folderApi = folderApi,
            productTitleApi = productTitleApi,
            receiveApi = receiveApi,
            navigator = navigator,
            logger = logger
        )
    }

    fun notificationsViewModel(): NotificationsScreenViewModel {
        return NotificationsScreenViewModel(
            notificationApi = notificationApi,
            logger = logger,
            loading = loading,
            failure = failure
        )
    }

    fun ordersViewModel(): OrdersScreenViewModel {
        return OrdersScreenViewModel(
            orderApi = orderApi,
            loading = loading,
            failure = failure,
            navigator = navigator,
            priceFormatter = formatter,
            dateFormatter = formatter,
            timeFormatter = formatter,
            logger = logger
        )
    }

    fun orderViewModel(id: Id): OrderScreenViewModel {
        return OrderScreenViewModel(
            id = id,
            orderApi = orderApi,
            formatPrice = formatter,
            formatDateTime = formatter,
            formatDecimal = formatter,
            navigator = navigator,
            logger = logger
        )
    }

    fun debtsViewModel(): DebtsScreenViewModel {
        return DebtsScreenViewModel(
            debtApi = debtApi,
            clientApi = clientApi,
            navigator = navigator,
            logger = logger,
            loading = loading,
            formatPrice = formatter,
            formatDate = formatter,
            formatDateTime = formatter,
            failure = failure,
        )
    }

    fun debtViewModel(id: Id): DebtScreenViewModel {
        return DebtScreenViewModel(
            id = id,
            debtApi = debtApi,
            formatPrice = formatter,
            formatDate = formatter,
            navigator = navigator,
            logger = logger
        )
    }

    fun profileViewModel(): ProfileContentViewModel {
        return ProfileContentViewModel(
            company = session.office.company,
            officeApi = officeApi,
            userApi = userApi,
            navigator = navigator,
            logger = logger
        )
    }

    fun productTitleViewModel(id: Id): ProductTitleScreenViewModel {
        return ProductTitleScreenViewModel(
            id = id,
            receiveApi = receiveApi,
            productTitleApi = productTitleApi,
            formatDecimal = formatter,
            formatDate = formatter,
            formatPrice = formatter,
            logger = logger
        )
    }

    fun receiveViewModel(id: Id): ReceiveScreenViewModel {
        return ReceiveScreenViewModel(
            id = id,
            receiveApi = receiveApi,
            formatPrice = formatter,
            formatDateTime = formatter,
            formatDecimal = formatter,
            loading = loading,
            navigator = navigator,
            logger = logger
        )
    }

    fun templateViewModel(id: Id): TemplateScreenViewModel {
        return TemplateScreenViewModel(
            id = id,
            templateApi = templateApi,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }
}