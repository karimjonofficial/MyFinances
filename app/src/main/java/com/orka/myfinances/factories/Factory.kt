package com.orka.myfinances.factories

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
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
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenViewModel
import com.orka.myfinances.ui.screens.client.viewmodel.ClientScreenViewModel
import com.orka.myfinances.ui.screens.clients.viewmodel.ClientsScreenViewModel
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtScreenViewModel
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtsScreenViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.profile.ProfileContentViewModel
import com.orka.myfinances.ui.screens.host.viewmodel.Formatter
import com.orka.myfinances.ui.screens.notification.NotificationScreenViewModel
import com.orka.myfinances.ui.screens.order.viewmodel.OrderScreenViewModel
import com.orka.myfinances.ui.screens.order.viewmodel.OrdersScreenViewModel
import com.orka.myfinances.ui.screens.product.add.viewmodel.AddProductTitleScreenViewModel
import com.orka.myfinances.ui.screens.product.viewmodel.ProductTitleScreenViewModel
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenViewModel
import com.orka.myfinances.ui.screens.receive.viewmodel.ReceiveScreenViewModel
import com.orka.myfinances.ui.screens.sale.viewmodel.SaleScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplateScreenViewModel
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.templates.viewmodel.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableSharedFlow

class Factory(
    private val session: Session,
    private val client: HttpClient,
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

    fun foldersViewModel(): FoldersContentViewModel {
        return FoldersContentViewModel(
            client = client,
            navigator = navigator,
            office = session.office,
            logger = logger
        )
    }

    fun templatesViewModel(): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(
            client = client,
            navigator = navigator,
            logger = logger,
            events = templateFlow
        )
    }

    fun addTemplateViewModel(): AddTemplateScreenViewModel {
        return AddTemplateScreenViewModel(
            client = client,
            navigator = navigator
        )
    }

    fun addProductViewModel(): AddProductTitleScreenViewModel {
        return AddProductTitleScreenViewModel(
            office = session.office,
            client = client,
            navigator = navigator,
            logger = logger
        )
    }

    fun warehouseViewModel(category: Category): WarehouseScreenViewModel {
        return WarehouseScreenViewModel(
            category = category,
            client = client,
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
            client = client,
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
            client = client,
            loading = loading,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }

    fun clientViewModel(id: Id): ClientScreenViewModel {
        return ClientScreenViewModel(
            id = id,
            client = client,
            navigator = navigator,
            logger = logger
        )
    }

    fun salesViewModel(): SaleContentViewModel {
        return SaleContentViewModel(
            loading = loading,
            failure = failure,
            client = client,
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
            client = client,
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
            client = client,
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
            client = client,
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
            client = client,
            id = id,
            navigator = navigator,
            logger = logger
        )
    }

    fun notificationsViewModel(): NotificationScreenViewModel {
        return NotificationScreenViewModel(
            client = client,
            logger = logger,
            loading = loading,
            failure = failure
        )
    }

    fun ordersViewModel(): OrdersScreenViewModel {
        return OrdersScreenViewModel(
            client = client,
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
            client = client,
            formatPrice = formatter,
            formatDate = formatter,
            formatTime = formatter,
            navigator = navigator,
            logger = logger
        )
    }

    fun debtsViewModel(): DebtsScreenViewModel {
        return DebtsScreenViewModel(
            client = client,
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
            client = client,
            formatPrice = formatter,
            formatDate = formatter,
            navigator = navigator,
            logger = logger
        )
    }

    fun profileViewModel(): ProfileContentViewModel {
        return ProfileContentViewModel(
            company = session.office.company,
            client = client,
            navigator = navigator,
            logger = logger
        )
    }

    fun productTitleViewModel(id: Id): ProductTitleScreenViewModel {
        return ProductTitleScreenViewModel(
            id = id,
            client = client,
            formatDecimal = formatter,
            formatDate = formatter,
            formatPrice = formatter,
            logger = logger
        )
    }

    fun receiveViewModel(id: Id): ReceiveScreenViewModel {
        return ReceiveScreenViewModel(
            id = id,
            client = client,
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
            client = client,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }
}