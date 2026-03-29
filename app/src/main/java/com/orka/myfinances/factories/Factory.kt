package com.orka.myfinances.factories

import com.orka.myfinances.R
import com.orka.myfinances.application.factories.Formatter
import com.orka.myfinances.application.viewmodels.basket.BasketContentViewModel
import com.orka.myfinances.application.viewmodels.checkout.CheckoutScreenViewModel
import com.orka.myfinances.application.viewmodels.client.details.ClientScreenViewModel
import com.orka.myfinances.application.viewmodels.client.list.ClientsScreenViewModel
import com.orka.myfinances.application.viewmodels.debt.details.DebtScreenViewModel
import com.orka.myfinances.application.viewmodels.debt.list.DebtsScreenViewModel
import com.orka.myfinances.application.viewmodels.folder.catalog.CatalogScreenViewModel
import com.orka.myfinances.application.viewmodels.folder.category.CategoryScreenViewModel
import com.orka.myfinances.application.viewmodels.folder.home.FoldersContentViewModel
import com.orka.myfinances.application.viewmodels.notification.NotificationsScreenViewModel
import com.orka.myfinances.application.viewmodels.order.details.OrderScreenViewModel
import com.orka.myfinances.application.viewmodels.order.list.OrdersScreenViewModel
import com.orka.myfinances.application.viewmodels.product.add.AddProductTitleScreenViewModel
import com.orka.myfinances.application.viewmodels.product.details.ProductTitleScreenViewModel
import com.orka.myfinances.application.viewmodels.product.list.ProductTitlesContentViewModel
import com.orka.myfinances.application.viewmodels.profile.ProfileContentViewModel
import com.orka.myfinances.application.viewmodels.receive.add.AddReceiveScreenViewModel
import com.orka.myfinances.application.viewmodels.receive.details.ReceiveScreenViewModel
import com.orka.myfinances.application.viewmodels.receive.list.ReceiveContentViewModel
import com.orka.myfinances.application.viewmodels.sale.details.SaleScreenViewModel
import com.orka.myfinances.application.viewmodels.sale.list.SaleContentViewModel
import com.orka.myfinances.application.viewmodels.stock.StockItemsContentViewModel
import com.orka.myfinances.application.viewmodels.template.add.AddTemplateScreenViewModel
import com.orka.myfinances.application.viewmodels.template.bottomsheet.TemplateBottomSheetViewModel
import com.orka.myfinances.application.viewmodels.template.details.TemplateScreenViewModel
import com.orka.myfinances.application.viewmodels.template.list.TemplatesScreenViewModel
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.notification.NotificationApi
import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.order.OrderApi1
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.ReceiveApi1
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.api.sale.SaleApi1
import com.orka.myfinances.data.api.stock.StockApi1
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.TemplateApi1
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.ProductTitleApi1
import com.orka.myfinances.data.api.user.UserApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.printer.pos.BluetoothPrinterImpl
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableSharedFlow

class Factory(
    private val session: Session,
    private val httpClient: HttpClient,
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

    private val clientApi = ClientApi(httpClient, session.office.company.id)
    private val folderApi = FolderApi(httpClient, session.office, folderFlow)
    private val templateApi = TemplateApi(httpClient, session.office)
    private val productTitleApi = ProductTitleApi(httpClient, productTitleFlow)
    private val receiveApi = ReceiveApi(httpClient, session.office, receiveFlow)
    private val saleApi = SaleApi(httpClient, session.office, saleFlow)
    private val orderApi = OrderApi(httpClient, session.office)
    private val debtApi = DebtApi(httpClient, session.office)
    private val officeApi = OfficeApi(httpClient)
    private val userApi = UserApi(httpClient)

    fun foldersViewModel(): FoldersContentViewModel {
        return FoldersContentViewModel(
            folderApi = folderApi,
            navigator = navigator,
            events = folderFlow,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun templatesViewModel(): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(
            templateApi = TemplateApi1(httpClient, session.office),
            navigator = navigator,
            loading = loading,
            failure = failure,
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
            folderApi = folderApi,
            productTitleApi = productTitleApi,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun stockItemsViewModel(id: Id): StockItemsContentViewModel {
        return StockItemsContentViewModel(
            categoryId = id,
            stockApi = StockApi1(session.office, httpClient),
            basketRepository = basketRepository,
            formatPrice = formatter,
            formatDecimal = formatter,
            stockEvents = stockFlow,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun productTitlesViewModel(id: Id): ProductTitlesContentViewModel {
        return ProductTitlesContentViewModel(
            categoryId = id,
            productTitleApi = ProductTitleApi1(session.office, httpClient),
            productTitleEvents = productTitleFlow,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun catalogViewModel(id: Id): CatalogScreenViewModel {
        return CatalogScreenViewModel(
            catalogId = id,
            folderApi = folderApi,
            events = folderFlow,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun basketViewModel(): BasketContentViewModel {
        return BasketContentViewModel(
            repository = basketRepository,
            navigator = navigator,
            formatPrice = formatter,
            formatDecimal = formatter,
            loading = loading,
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
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun salesViewModel(): SaleContentViewModel {
        return SaleContentViewModel(
            loading = loading,
            failure = failure,
            saleApi = SaleApi1(session.office, httpClient),
            events = saleFlow,
            navigator = navigator,
            priceFormatter = formatter,
            formatLocalDate = formatter,
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
            failure = failure,
            formatDecimal = formatter,
            navigator = navigator,
            logger = logger
        )
    }

    fun receivesViewModel(): ReceiveContentViewModel {
        return ReceiveContentViewModel(
            receiveApi = ReceiveApi1(session.office, httpClient),
            events = receiveFlow,
            loading = loading,
            failure = failure,
            navigator = navigator,
            formatPrice = formatter,
            formatLocalDate = formatter,
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
            loading = loading,
            failure = failure,
            printerState = printer.state
        )
    }

    fun addReceiveViewModel(id: Id): AddReceiveScreenViewModel {
        return AddReceiveScreenViewModel(
            categoryId = id,
            folderApi = folderApi,
            productTitleApi = productTitleApi,
            receiveApi = receiveApi,
            navigator = navigator,
            flow = stockFlow,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun notificationsViewModel(): NotificationsScreenViewModel {
        return NotificationsScreenViewModel(
            api = NotificationApi(httpClient),
            formatLocalDate = formatter,
            formatTime = formatter,
            logger = logger,
            loading = loading,
            failure = failure
        )
    }

    fun ordersViewModel(): OrdersScreenViewModel {
        return OrdersScreenViewModel(
            orderApi = OrderApi1(session.office, httpClient),
            loading = loading,
            failure = failure,
            navigator = navigator,
            formatPrice = formatter,
            formatTime = formatter,
            formatLocalDate = formatter,
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
            loading = loading,
            failure = failure,
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
            formatTime = formatter,
            formatLocalDate = formatter,
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
            loading = loading,
            logger = logger
        )
    }

    fun profileViewModel(): ProfileContentViewModel {
        return ProfileContentViewModel(
            company = session.office.company,
            officeApi = officeApi,
            userApi = userApi,
            navigator = navigator,
            loading = loading,
            logger = logger
        )
    }

    fun productTitleViewModel(id: Id): ProductTitleScreenViewModel {
        return ProductTitleScreenViewModel(
            productId = id,
            receiveApi = receiveApi,
            productTitleApi = productTitleApi,
            formatDecimal = formatter,
            formatDate = formatter,
            formatPrice = formatter,
            flow = stockFlow,
            loading = loading,
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
            loading = loading,
            logger = logger
        )
    }

    fun categoryViewModel(id: Id): CategoryScreenViewModel {
        return CategoryScreenViewModel(
            categoryId = id,
            folderApi = folderApi,
            loading = loading,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }

    fun templateBottomSheetViewModel(): TemplateBottomSheetViewModel {
        return TemplateBottomSheetViewModel(
            templateApi = TemplateApi1(httpClient, session.office),
            loading = loading,
            failure = failure,
            logger = logger
        )
    }
}
