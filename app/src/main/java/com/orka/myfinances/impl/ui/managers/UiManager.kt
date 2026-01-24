package com.orka.myfinances.impl.ui.managers

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.data.repositories.debt.DebtRepository
import com.orka.myfinances.data.repositories.folder.CategoryRepository
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.ProductTitleRepository
import com.orka.myfinances.data.repositories.receive.ReceiveRepository
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.factories.viewmodel.Factory
import com.orka.myfinances.lib.extensions.models.makeSession
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.session.SessionManager
import com.orka.myfinances.ui.managers.session.UiState
import com.orka.myfinances.ui.screens.login.LoginScreenViewModel
import kotlinx.coroutines.flow.asStateFlow

class UiManager(
    logger: Logger,
    private val storage: LocalSessionStorage,
    private val provider: ApiProvider
) : ViewModel<UiState>(
    initialState = UiState.Initial,
    logger = logger
), SessionManager {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val sessionModel = storage.get()

        if (sessionModel != null) {
            val session = sessionModel.toSession()
            openSession(session)
        } else {
            val apiService = provider.getCredentialApiService()
            val viewModel = LoginScreenViewModel(logger, apiService, this)
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
        val factory = factory()
        val initialBackStack = listOf(Destination.Home)
        val navigationManager = NavigationManager(initialBackStack, logger)
        setState(UiState.SignedIn(session, navigationManager, factory))
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
    private fun factory(): Factory {
        val templateRepository = TemplateRepository()
        val folderRepository = FolderRepository(templateRepository)
        val categoryRepository = CategoryRepository(folderRepository)
        val stockRepository = StockRepository()
        val titleRepository = ProductTitleRepository()
        val productRepository = ProductRepository(titleRepository)
        val basketRepository = BasketRepository(productRepository)
        val saleRepository = SaleRepository()
        val receiveRepository = ReceiveRepository()
        val orderRepository = OrderRepository()
        val clientRepository = ClientRepository()
        val debtRepository = DebtRepository()
        val notificationRepository = NotificationRepository()

        return Factory(
            folderRepository = folderRepository,
            templateRepository = templateRepository,
            productRepository = productRepository,
            categoryRepository = categoryRepository,
            stockRepository = stockRepository,
            basketRepository = basketRepository,
            clientRepository = clientRepository,
            saleRepository = saleRepository,
            orderRepository = orderRepository,
            receiveRepository = receiveRepository,
            debtRepository = debtRepository,
            notificationRepository = notificationRepository,
            logger = logger
        )
    }
}