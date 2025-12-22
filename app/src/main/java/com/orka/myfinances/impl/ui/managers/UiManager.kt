package com.orka.myfinances.impl.ui.managers

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.repositories.receive.ReceiveRepository
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.factories.viewmodel.ViewModelProvider
import com.orka.myfinances.fixtures.data.api.ProductApiServiceImpl
import com.orka.myfinances.fixtures.data.api.StockApiServiceImpl
import com.orka.myfinances.fixtures.data.repositories.FolderRepositoryImpl
import com.orka.myfinances.fixtures.data.repositories.TemplateRepositoryImpl
import com.orka.myfinances.impl.factories.viewmodels.CatalogScreenViewModelProviderImpl
import com.orka.myfinances.impl.factories.viewmodels.ViewModelProviderImpl
import com.orka.myfinances.impl.factories.viewmodels.WarehouseScreenViewModelProviderImpl
import com.orka.myfinances.lib.extensions.models.makeSession
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.session.SessionManager
import com.orka.myfinances.ui.managers.session.UiState
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentViewModel
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.ui.screens.order.OrdersScreenViewModel
import com.orka.myfinances.ui.screens.checkout.CheckoutScreenViewModel
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import com.orka.myfinances.ui.screens.clients.ClientsScreenViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.FoldersContentViewModel
import com.orka.myfinances.ui.screens.login.LoginScreenViewModel
import com.orka.myfinances.ui.screens.notification.NotificationScreenViewModel
import com.orka.myfinances.ui.screens.stock.AddStockItemScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow

class UiManager(
    logger: Logger,
    private val storage: LocalSessionStorage,
    private val provider: ApiProvider,
    coroutineScope: CoroutineScope
) : ViewModel<UiState>(
    initialState = UiState.Initial,
    logger = logger,
    coroutineScope = coroutineScope
), SessionManager {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val sessionModel = storage.get()

        if (sessionModel != null) {
            val session = sessionModel.toSession()
            openSession(session)
        } else {
            val apiService = provider.getCredentialApiService()
            val viewModel = LoginScreenViewModel(logger, apiService, this, newScope())
            setState(UiState.Guest(viewModel))
        }
    }

    override fun open(credential: Credential) {
        launch {
            val session = getSession(credential)
            if (session != null) openSession(session)
        }
    }

    override fun store(credential: Credential) {
        launch {
            val session = getSession(credential)

            if (session != null) {
                storage.store(session.toModel())
                openSession(session)
            }
        }
    }

    private fun openSession(session: Session) {
        val provider = viewModelProvider()
        val foldersViewModel = provider.foldersViewModel()
        val basketViewModel = provider.basketViewModel()
        val initialBackStack = listOf(Destination.Home(foldersViewModel, basketViewModel))
        val navigationManager =
            NavigationManagerImpl(initialBackStack, provider, logger, newScope())
        setState(UiState.SignedIn(session, navigationManager))
    }
    private suspend fun getSession(credential: Credential): Session? {
        val userApiService = provider.getUserApiService()
        val companyApiService = provider.getCompanyApiService()
        val companyOfficeApiService = provider.getCompanyOfficeApiService()

        val userModel = userApiService.get(credential)
        val companyModel = companyApiService.get(credential)
        val companyOfficeModel = companyOfficeApiService.get(credential)

        return if (userModel != null && companyModel != null && companyOfficeModel != null) {
            makeSession(credential, userModel, companyOfficeModel, companyModel)
        } else null
    }
    private fun viewModelProvider(): ViewModelProvider {
        val folderRepository = FolderRepositoryImpl()
        val productApiService = ProductApiServiceImpl()
        val warehouseApiService = StockApiServiceImpl()
        val templateRepository = TemplateRepositoryImpl()
        val stockRepository = StockRepository(warehouseApiService)
        val productRepository = ProductRepository(productApiService)
        val basketRepository = BasketRepository(productRepository)
        val saleRepository = SaleRepository()
        val receiveRepository = ReceiveRepository()
        val orderRepository = OrderRepository()
        val clientRepository = ClientRepository()

        val foldersContentViewModel = FoldersContentViewModel(
            repository = folderRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val basketContentViewModel = BasketContentViewModel(
            repository = basketRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val addTemplateScreenViewModel = AddTemplateScreenViewModel(
            repository = templateRepository,
            coroutineScope = newScope()
        )
        val templatesScreenViewModel = TemplatesScreenViewModel(
            repository = templateRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val addProductScreenViewModel = AddProductScreenViewModel(
            productRepository = productRepository,
            stockRepository = stockRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val warehouseScreenViewModelProvider = WarehouseScreenViewModelProviderImpl(
            productRepository = productRepository,
            stockRepository = stockRepository,
            addToBasket = { basketContentViewModel.increase(it.product.id) },
            logger = logger,
            coroutineScope = newScope()
        )
        val catalogScreenViewModelProvider = CatalogScreenViewModelProviderImpl(
            repository = folderRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val clientsScreenViewModel = ClientsScreenViewModel(
            loading = "Loading",
            failure = "Failure",
            repository = clientRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val saleViewModel = SaleContentViewModel(
            loading = "Loading",
            failure = "Failure",
            repository = saleRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val receiveViewModel = ReceiveContentViewModel(
            loading = "Loading",
            failure = "Failure",
            repository = receiveRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val checkoutScreenViewModel = CheckoutScreenViewModel(
            saleRepository = saleRepository,
            orderRepository = orderRepository,
            basketRepository = basketRepository,
            clientRepository = clientRepository,
            logger = logger,
            coroutineScope = newScope()
        )
        val addStockItemScreenViewModel = AddStockItemScreenViewModel(
            repository = receiveRepository,
            coroutineScope = newScope()
        )
        val ordersScreenViewModel = OrdersScreenViewModel(
            repository = orderRepository,
            loading = "Loading",
            failure = "Failure",
            logger = logger,
            coroutineScope = newScope()
        )
        val notificationScreenViewModel = NotificationScreenViewModel(
            repository = NotificationRepository(),
            logger = logger,
            coroutineScope = newScope()
        )

        return ViewModelProviderImpl(
            addTemplateScreenViewModel = addTemplateScreenViewModel,
            templatesScreenViewModel = templatesScreenViewModel,
            foldersContentViewModel = foldersContentViewModel,
            addProductScreenViewModel = addProductScreenViewModel,
            warehouseScreenViewModelProvider = warehouseScreenViewModelProvider,
            catalogScreenViewModelProvider = catalogScreenViewModelProvider,
            basketContentViewModel = basketContentViewModel,
            clientsScreenViewModel = clientsScreenViewModel,
            saleViewModel = saleViewModel,
            receiveViewModel = receiveViewModel,
            checkoutViewModel = checkoutScreenViewModel,
            addStockItemViewModel = addStockItemScreenViewModel,
            ordersViewModel = ordersScreenViewModel,
            notificationsViewModel = notificationScreenViewModel
        )
    }
}