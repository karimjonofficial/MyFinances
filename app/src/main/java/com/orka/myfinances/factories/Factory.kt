package com.orka.myfinances.factories

import com.orka.myfinances.application.data.repositories.DefaultsRepository
import com.orka.myfinances.application.data.repositories.PinnedCategoriesRepository
import com.orka.myfinances.application.viewmodels.basket.BasketContentViewModel
import com.orka.myfinances.application.viewmodels.checkout.CheckoutScreenViewModel
import com.orka.myfinances.application.viewmodels.client.add.AddClientViewModel
import com.orka.myfinances.application.viewmodels.client.bottomsheet.ClientBottomSheetViewModel
import com.orka.myfinances.application.viewmodels.client.details.ClientScreenViewModel
import com.orka.myfinances.application.viewmodels.client.list.ClientsScreenViewModel
import com.orka.myfinances.application.viewmodels.debt.details.DebtScreenViewModel
import com.orka.myfinances.application.viewmodels.debt.history.DebtsHistoryContentViewModel
import com.orka.myfinances.application.viewmodels.debt.list.DebtsScreenViewModel
import com.orka.myfinances.application.viewmodels.defaults.category.SelectDefaultCategoryViewModel
import com.orka.myfinances.application.viewmodels.folder.catalog.CatalogScreenViewModel
import com.orka.myfinances.application.viewmodels.folder.category.CategoryScreenViewModel
import com.orka.myfinances.application.viewmodels.folder.home.FoldersContentViewModel
import com.orka.myfinances.application.viewmodels.notification.NotificationsScreenViewModel
import com.orka.myfinances.application.viewmodels.order.details.OrderScreenViewModel
import com.orka.myfinances.application.viewmodels.order.list.completed.OrdersHistoryContentViewModel
import com.orka.myfinances.application.viewmodels.order.list.incompleted.OrdersListScreenViewModel
import com.orka.myfinances.application.viewmodels.product.add.AddProductTitleScreenViewModel
import com.orka.myfinances.application.viewmodels.product.bottomsheet.ProductTitleBottomSheetViewModel
import com.orka.myfinances.application.viewmodels.product.details.ProductTitleScreenViewModel
import com.orka.myfinances.application.viewmodels.product.edit.EditProductTitleScreenViewModel
import com.orka.myfinances.application.viewmodels.product.list.ProductTitlesContentViewModel
import com.orka.myfinances.application.viewmodels.profile.ProfileContentViewModel
import com.orka.myfinances.application.viewmodels.receive.add.AddReceiveScreenViewModel
import com.orka.myfinances.application.viewmodels.receive.details.ReceiveScreenViewModel
import com.orka.myfinances.application.viewmodels.receive.list.ReceiveContentViewModel
import com.orka.myfinances.application.viewmodels.sale.details.SaleScreenViewModel
import com.orka.myfinances.application.viewmodels.sale.list.SaleContentViewModel
import com.orka.myfinances.application.viewmodels.settings.SettingsScreenViewModel
import com.orka.myfinances.application.viewmodels.stock.StockItemsContentViewModel
import com.orka.myfinances.application.viewmodels.template.add.AddTemplateScreenViewModel
import com.orka.myfinances.application.viewmodels.template.bottomsheet.TemplateBottomSheetViewModel
import com.orka.myfinances.application.viewmodels.template.details.TemplateScreenViewModel
import com.orka.myfinances.application.viewmodels.template.list.TemplatesScreenViewModel
import com.orka.myfinances.data.api.branch.BranchApi
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.notification.NotificationApi
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.user.UserApi
import com.orka.myfinances.data.database.AppDatabase
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.repositories.basket.BasketRepositoryImpl
import com.orka.myfinances.data.repositories.branch.BranchRepository
import com.orka.myfinances.data.repositories.client.ClientEvent
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.data.repositories.debt.DebtEvent
import com.orka.myfinances.data.repositories.debt.DebtRepository
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.product.title.ProductTitleRepository
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.data.repositories.receive.ReceiveRepository
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.data.repositories.user.UserRepository
import com.orka.myfinances.format.Formatter
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.managers.SessionManager
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableSharedFlow

class Factory(
    private val session: Session,
    httpClient: HttpClient,
    private val printer: Printer,
    private val logger: Logger,
    private val navigator: Navigator,
    private val formatter: Formatter,
    private val sessionManager: SessionManager,
    database: AppDatabase,
    private val loading: UiText,
    private val failure: UiText
) {
    private val stockFlow = MutableSharedFlow<StockEvent>()
    private val templateFlow = MutableSharedFlow<TemplateEvent>()
    private val productTitleFlow = MutableSharedFlow<ProductTitleEvent>()
    private val folderFlow = MutableSharedFlow<FolderEvent>()
    private val saleFlow = MutableSharedFlow<SaleEvent>()
    private val receiveFlow = MutableSharedFlow<ReceiveEvent>()
    private val orderFlow = MutableSharedFlow<OrderEvent>()
    private val debtFlow = MutableSharedFlow<DebtEvent>()
    private val clientFlow = MutableSharedFlow<ClientEvent>()

    private val clientApi = ClientApi(httpClient)
    private val folderApi = FolderApi(httpClient)
    private val productTitleApi = ProductTitleApi(session.branchId, httpClient)
    private val templateApi = TemplateApi(httpClient)
    private val receiveApi = ReceiveApi(httpClient)
    private val saleApi = SaleApi(session.branchId, httpClient)
    private val orderApi = OrderApi(httpClient)
    private val debtApi = DebtApi(httpClient)
    private val branchApi = BranchApi(httpClient)
    private val userApi = UserApi(httpClient)
    private val stockApi = StockApi(httpClient)

    private val pinnedCategoriesDao = database.pinnedCategoriesDao()
    private val defaultsDao = database.defaultsDao()

    private val basketRepository = BasketRepositoryImpl()
    private val pinnedCategoriesRepository = PinnedCategoriesRepository(pinnedCategoriesDao)
    private val stockRepository = StockRepository(session.branchId, stockApi, stockFlow)
    private val folderRepository = FolderRepository(session.branchId, folderFlow, folderApi)
    private val templateRepository = TemplateRepository(session.branchId, templateApi, templateFlow)
    private val clientRepository = ClientRepository(session.companyId, clientApi, clientFlow)
    private val debtRepository = DebtRepository(session.branchId, debtApi, debtFlow)
    private val orderRepository = OrderRepository(session.branchId, orderApi, orderFlow)
    private val notificationRepository = NotificationRepository(NotificationApi(httpClient))
    private val branchRepository = BranchRepository(session.companyId, branchApi)
    private val userRepository = UserRepository(userApi)
    private val receiveRepository = ReceiveRepository(session.branchId, receiveApi, receiveFlow, stockFlow)
    private val productTitleRepository = ProductTitleRepository(productTitleApi, productTitleFlow)
    private val saleRepository = SaleRepository(saleApi, saleFlow, stockFlow)
    private val defaultsRepository = DefaultsRepository(defaultsDao, pinnedCategoriesRepository)

    fun foldersViewModel(): FoldersContentViewModel {
        return FoldersContentViewModel(
            getTop = folderRepository,
            getDefaultCategory = defaultsRepository,
            addFolder = folderRepository,
            addTitle = productTitleRepository,
            addReceive = receiveRepository,
            pinnedCategoriesRepository = pinnedCategoriesRepository,
            navigator = navigator,
            folderFlow = folderFlow,
            defaultsFlow = defaultsRepository.flow,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun templatesViewModel(): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(
            getChunk = templateRepository,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger,
            formatDecimal = formatter,
            events = templateFlow
        )
    }

    fun addTemplateViewModel(): AddTemplateScreenViewModel {
        return AddTemplateScreenViewModel(
            insert = templateRepository,
            navigator = navigator
        )
    }

    fun addProductViewModel(categoryId: Id): AddProductTitleScreenViewModel {
        return AddProductTitleScreenViewModel(
            categoryId = categoryId,
            getFolders = folderRepository,
            insertTitle = productTitleRepository,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun editProductViewModel(id: Id): EditProductTitleScreenViewModel {
        return EditProductTitleScreenViewModel(
            productId = id,
            getFolders = folderRepository,
            productTitleRepository = productTitleRepository,
            updateTitle = productTitleRepository,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun stockItemsViewModel(id: Id): StockItemsContentViewModel {
        return StockItemsContentViewModel(
            categoryId = id,
            getByCategory = stockRepository,
            stockEvents = stockFlow,
            basketRepository = basketRepository,
            formatPrice = formatter,
            formatDecimal = formatter,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun productTitlesViewModel(id: Id): ProductTitlesContentViewModel {
        return ProductTitlesContentViewModel(
            categoryId = id,
            getByCategory = productTitleRepository,
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
            getByParent = folderRepository,
            getById = folderRepository,
            add = folderRepository,
            events = folderFlow,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun basketViewModel(): BasketContentViewModel {
        return BasketContentViewModel(
            basketRepository = basketRepository,
            stockRepository = stockRepository,
            navigator = navigator,
            formatPrice = formatter,
            formatDecimal = formatter,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun clientsViewModel(): ClientsScreenViewModel {
        return ClientsScreenViewModel(
            getChunk = clientRepository,
            insert = clientRepository,
            events = clientFlow,
            loading = loading,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }

    fun clientViewModel(id: Id): ClientScreenViewModel {
        return ClientScreenViewModel(
            id = id,
            getById = clientRepository,
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
            getChunk = saleRepository,
            events = saleFlow,
            navigator = navigator,
            formatPrice = formatter,
            formatLocalDate = formatter,
            formatTime = formatter,
            formatDecimal = formatter,
            logger = logger,
        )
    }

    fun saleViewModel(id: Id): SaleScreenViewModel {
        return SaleScreenViewModel(
            id = id,
            getById = saleRepository,
            printer = printer,
            formatPrice = formatter,
            formatDate = formatter,
            formatTime = formatter,
            loading = loading,
            failure = failure,
            formatDecimal = formatter,
            navigator = navigator,
            logger = logger
        )
    }

    fun receivesViewModel(): ReceiveContentViewModel {
        return ReceiveContentViewModel(
            getChunk = receiveRepository,
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
            addSale = saleRepository,
            insertOrder = orderRepository,
            insertDebt = debtRepository,
            stockRepository = stockRepository,
            basketRepository = basketRepository,
            logger = logger,
            navigator = navigator,
            formatPrice = formatter,
            formatDecimal = formatter,
            printer = printer,
            loading = loading,
            failure = failure
        )
    }

    fun clientBottomSheetViewModel(): ClientBottomSheetViewModel {
        return ClientBottomSheetViewModel(
            getChunk = clientRepository,
            events = clientFlow,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun addReceiveViewModel(id: Id): AddReceiveScreenViewModel {
        return AddReceiveScreenViewModel(
            categoryId = id,
            getFolder = folderRepository,
            insertReceive = receiveRepository,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun productTitleBottomSheetViewModel(id: Id): ProductTitleBottomSheetViewModel {
        return ProductTitleBottomSheetViewModel(
            categoryId = id,
            getByCategory = productTitleRepository,
            flow = productTitleFlow,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun notificationsViewModel(): NotificationsScreenViewModel {
        return NotificationsScreenViewModel(
            getChunk = notificationRepository,
            readNotification = notificationRepository,
            formatLocalDate = formatter,
            formatTime = formatter,
            logger = logger,
            loading = loading,
            failure = failure
        )
    }

    fun ordersViewModel(): OrdersListScreenViewModel {
        return OrdersListScreenViewModel(
            getOrdersChunk = orderRepository,
            events = orderFlow,
            loading = loading,
            failure = failure,
            navigator = navigator,
            formatDecimal = formatter,
            formatPrice = formatter,
            formatDate = formatter,
            formatLocalDate = formatter,
            logger = logger
        )
    }

    fun ordersHistoryViewModel(): OrdersHistoryContentViewModel {
        return OrdersHistoryContentViewModel(
            getOrdersChunk = orderRepository,
            events = orderFlow,
            loading = loading,
            failure = failure,
            navigator = navigator,
            formatDecimal = formatter,
            formatPrice = formatter,
            formatTime = formatter,
            formatLocalDate = formatter,
            logger = logger
        )
    }

    fun orderViewModel(id: Id): OrderScreenViewModel {
        return OrderScreenViewModel(
            id = id,
            getById = orderRepository,
            completeOrder = orderRepository,
            setEndDate = orderRepository,
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
            getChunk = debtRepository,
            insert = debtRepository,
            events = debtFlow,
            navigator = navigator,
            logger = logger,
            loading = loading,
            formatPrice = formatter,
            formatLocalDate = formatter,
            formatTime = formatter,
            failure = failure,
        )
    }

    fun debtViewModel(id: Id): DebtScreenViewModel {
        return DebtScreenViewModel(
            id = id,
            getById = debtRepository,
            setPaid = debtRepository,
            setNotified = debtRepository,
            formatPrice = formatter,
            formatDate = formatter,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun profileViewModel(): ProfileContentViewModel {
        return ProfileContentViewModel(
            branchId = session.branchId,
            getBranches = branchRepository,
            getMe = userRepository,
            navigator = navigator,
            sessionManager = sessionManager,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun productTitleViewModel(id: Id): ProductTitleScreenViewModel {
        return ProductTitleScreenViewModel(
            productId = id,
            getById = productTitleRepository,
            insertReceive = receiveRepository,
            productTitleEvents = productTitleFlow,
            formatDecimal = formatter,
            formatDate = formatter,
            formatPrice = formatter,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun receiveViewModel(id: Id): ReceiveScreenViewModel {
        return ReceiveScreenViewModel(
            id = id,
            getById = receiveRepository,
            formatPrice = formatter,
            formatDateTime = formatter,
            formatDecimal = formatter,
            loading = loading,
            navigator = navigator,
            failure = failure,
            logger = logger
        )
    }

    fun templateViewModel(id: Id): TemplateScreenViewModel {
        return TemplateScreenViewModel(
            id = id,
            getById = templateRepository,
            failure = failure,
            navigator = navigator,
            loading = loading,
            logger = logger
        )
    }

    fun categoryViewModel(id: Id): CategoryScreenViewModel {
        return CategoryScreenViewModel(
            categoryId = id,
            getById = folderRepository,
            loading = loading,
            failure = failure,
            navigator = navigator,
            logger = logger
        )
    }

    fun templateBottomSheetViewModel(): TemplateBottomSheetViewModel {
        return TemplateBottomSheetViewModel(
            getChunk = templateRepository,
            flow = templateFlow,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun debtHistoryViewModel(): DebtsHistoryContentViewModel {
        return DebtsHistoryContentViewModel(
            getDebtsChunk = debtRepository,
            events = debtFlow,
            formatPrice = formatter,
            formatLocalDate = formatter,
            formatTime = formatter,
            loading = loading,
            failure = failure,
            logger = logger,
            navigator = navigator
        )
    }

    fun addClientViewModel(): AddClientViewModel {
        return AddClientViewModel(
            insert = clientRepository
        )
    }

    fun settingsViewModel(): SettingsScreenViewModel {
        return SettingsScreenViewModel(
            defaultsRepository = defaultsRepository,
            get = folderRepository,
            flow = defaultsRepository.flow,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }

    fun selectDefaultCategoryViewModel(): SelectDefaultCategoryViewModel {
        return SelectDefaultCategoryViewModel(
            foldersRepository = folderRepository,
            getDefaultCategory = defaultsRepository,
            setDefaultCategory = defaultsRepository,
            flow = defaultsRepository.flow,
            navigator = navigator,
            loading = loading,
            failure = failure,
            logger = logger
        )
    }
}
