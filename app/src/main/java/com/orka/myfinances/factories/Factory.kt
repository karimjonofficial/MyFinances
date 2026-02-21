package com.orka.myfinances.factories

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.data.repositories.debt.DebtRepository
import com.orka.myfinances.data.repositories.folder.CategoryRepository
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleRepository
import com.orka.myfinances.data.repositories.receive.ReceiveRepository
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenViewModel
import com.orka.myfinances.ui.screens.clients.viewmodel.ClientsScreenViewModel
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtScreenViewModel
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtsScreenViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.ProfileContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersContentViewModel
import com.orka.myfinances.ui.screens.host.Formatter
import com.orka.myfinances.ui.screens.notification.NotificationScreenViewModel
import com.orka.myfinances.ui.screens.order.viewmodel.OrderScreenViewModel
import com.orka.myfinances.ui.screens.order.viewmodel.OrdersScreenViewModel
import com.orka.myfinances.ui.screens.product.add.viewmodel.AddProductTitleScreenViewModel
import com.orka.myfinances.ui.screens.product.viewmodel.ProductTitleScreenViewModel
import com.orka.myfinances.ui.screens.receive.viewmodel.ReceiveScreenViewModel
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenViewModel
import com.orka.myfinances.ui.screens.sale.viewmodel.SaleScreenViewModel
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.templates.viewmodel.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

class Factory(
    private val officeApi: OfficeApi,
    private val productTitleRepository: ProductTitleRepository,
    private val folderRepository: FolderRepository,
    private val templateRepository: TemplateRepository,
    private val categoryRepository: CategoryRepository,
    private val stockRepository: StockRepository,
    private val basketRepository: BasketRepository,
    private val clientRepository: ClientRepository,
    private val saleRepository: SaleRepository,
    private val orderRepository: OrderRepository,
    private val receiveRepository: ReceiveRepository,
    private val debtRepository: DebtRepository,
    private val notificationRepository: NotificationRepository,
    private val logger: Logger,
    private val navigator: Navigator,
    private val formatter: Formatter,
) {
    private val loading = UiText.Res(R.string.loading)
    private val failure = UiText.Res(R.string.failure)

    fun foldersViewModel(): FoldersContentViewModel {
        return FoldersContentViewModel(
            get = folderRepository,
            add = folderRepository,
            templateRepository = templateRepository,
            navigator = navigator,
            logger = logger
        )
    }

    fun templatesViewModel(): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(
            get = templateRepository,
            events = templateRepository.events,
            navigator = navigator,
            logger = logger
        )
    }

    fun addTemplateViewModel(): AddTemplateScreenViewModel {
        return AddTemplateScreenViewModel(add = templateRepository)
    }

    fun addProductViewModel(): AddProductTitleScreenViewModel {
        return AddProductTitleScreenViewModel(
            productTitleRepository = productTitleRepository,
            categoryRepository = categoryRepository,
            navigator = navigator,
            logger = logger
        )
    }

    fun warehouseViewModel(category: Category): WarehouseScreenViewModel {
        return WarehouseScreenViewModel(
            category = category,
            getProductTitles = productTitleRepository,
            getStockItems = stockRepository,
            basketRepository = basketRepository,
            productTitleEvents = productTitleRepository.events,
            stockEvents = stockRepository.events,
            navigator = navigator,
            priceFormatter = formatter,
            decimalFormatter = formatter,
            logger = logger
        )
    }

    fun catalogViewModel(catalog: Catalog): CatalogScreenViewModel {
        return CatalogScreenViewModel(
            catalog = catalog,
            getByParameter = folderRepository,
            add = folderRepository,
            get = templateRepository,
            events = folderRepository.events,
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
            get = clientRepository,
            add = clientRepository,
            loading = loading,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }

    fun salesViewModel(): SaleContentViewModel {
        return SaleContentViewModel(
            loading = loading,
            failure = failure,
            repository = saleRepository,
            events = saleRepository.events,
            navigator = navigator,
            formatNames = formatter,
            priceFormatter = formatter,
            dateFormatter = formatter,
            timeFormatter = formatter,
            logger = logger,
        )
    }

    fun saleViewModel(sale: Sale): SaleScreenViewModel {
        return SaleScreenViewModel(
            sale = sale,
            formatPrice = formatter,
            formatDateTime = formatter,
            loading = loading,
            formatDecimal = formatter,
            logger = logger
        )
    }

    fun receivesViewModel(): ReceiveContentViewModel {
        return ReceiveContentViewModel(
            repository = receiveRepository,
            events = receiveRepository.events,
            loading = loading,
            failure = failure,
            navigator = navigator,
            formatNames = formatter,
            priceFormatter = formatter,
            dateFormatter = formatter,
            timeFormatter = formatter,
            logger = logger
        )
    }

    fun checkoutViewModel(): CheckoutScreenViewModel {
        return CheckoutScreenViewModel(
            addSale = saleRepository,
            addOrder = orderRepository,
            basketRepository = basketRepository,
            get = clientRepository,
            logger = logger,
            navigator = navigator,
            formatPrice = formatter,
            formatDecimal = formatter
        )
    }

    fun addReceiveViewModel(category: Category): AddReceiveScreenViewModel {
        return AddReceiveScreenViewModel(
            get = productTitleRepository,
            add = receiveRepository,
            category = category,
            loading = loading,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }

    fun notificationsViewModel(): NotificationScreenViewModel {
        return NotificationScreenViewModel(
            repository = notificationRepository,
            logger = logger,
            loading = loading,
            failure = failure,
        )
    }

    fun ordersViewModel(): OrdersScreenViewModel {
        return OrdersScreenViewModel(
            get = orderRepository,
            loading = loading,
            failure = failure,
            navigator = navigator,
            priceFormatter = formatter,
            dateFormatter = formatter,
            timeFormatter = formatter,
            logger = logger
        )
    }

    fun orderViewModel(order: Order): OrderScreenViewModel {
        return OrderScreenViewModel(
            order = order,
            formatPrice = formatter,
            formatDate = formatter,
            formatDecimal = formatter,
            logger = logger
        )
    }

    fun debtsViewModel(): DebtsScreenViewModel {
        return DebtsScreenViewModel(
            getDebts = debtRepository,
            add = debtRepository,
            getClients = clientRepository,
            navigator = navigator,
            logger = logger,
            loading = loading,
            formatPrice = formatter,
            formatDate = formatter,
            formatDateTime = formatter,
            failure = failure,
        )
    }

    fun debtViewModel(debt: Debt): DebtScreenViewModel {
        return DebtScreenViewModel(
            debt = debt,
            formatPrice = formatter,
            formatDate = formatter,
            logger = logger
        )
    }

    fun profileViewModel(): ProfileContentViewModel {
        return ProfileContentViewModel(
            loading = loading,
            failure = failure,
            get = officeApi,
            navigator = navigator,
            logger = logger
        )
    }

    fun productTitleViewModel(productTitle: ProductTitle): ProductTitleScreenViewModel {
        return ProductTitleScreenViewModel(
            productTitle = productTitle,
            repository = receiveRepository,
            formatDecimal = formatter,
            formatDate = formatter,
            formatPrice = formatter,
            loading = loading,
            logger = logger
        )
    }

    fun receiveViewModel(receive: Receive): ReceiveScreenViewModel {
        return ReceiveScreenViewModel(
            receive = receive,
            formatPrice = formatter,
            formatDateTime = formatter,
            formatDecimal = formatter,
            loading = loading,
            logger = logger
        )
    }
}