package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SingleStateViewModel
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Office
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
import com.orka.myfinances.data.zipped.OfficeModel
import com.orka.myfinances.factories.ApiProvider
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.extensions.models.makeSession
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.navigation.NavigationManager
import com.orka.myfinances.ui.screens.login.LoginScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow

class UiManager(
    logger: Logger,
    private val storage: LocalSessionStorage,
    private val provider: ApiProvider
) : SingleStateViewModel<UiState>(
    initialState = UiState.Initial,
    logger = logger
), SessionManager {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            delay(100)
            val sessionModel = storage.get()

            if (sessionModel != null) {
                val session = sessionModel.toSession()
                openSession(session)
            } else setStateGuest()
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

    override fun setOffice(credential: Credential, office: Office) {
        launch {
            val session = getSession(
                credential = credential,
                officeModel = office.toModel()
            )

            if (session != null)
                openSession(session)
            else setStateGuest()
        }
    }

    private fun setStateGuest() {
        val apiService = provider.getCredentialApiService()
        val viewModel = LoginScreenViewModel(logger, apiService, this)
        state.value = UiState.Guest(viewModel)
    }

    private fun openSession(session: Session) {
        val factory = factory()
        val initialBackStack = listOf(Destination.Home)
        val navigationManager = NavigationManager(initialBackStack, logger)
        setState(UiState.SignedIn(session, navigationManager, factory))
    }

    private suspend fun getSession(credential: Credential): Session? {
        val officeApi = provider.officeApi()
        val officeModel = officeApi.get(credential)

        return if (officeModel != null) {
            getSession(credential, officeModel)
        } else null
    }

    private suspend fun getSession(
        credential: Credential,
        officeModel: OfficeModel
    ): Session? {
        val userApiService = provider.getUserApiService()
        val companyApiService = provider.getCompanyApiService()

        val userModel = userApiService.get(credential)
        val companyModel = companyApiService.get(credential)

        return if (userModel != null && companyModel != null)
            makeSession(credential, userModel, officeModel, companyModel)
        else null
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
            officeApi = provider.officeApi(),
            logger = logger
        )
    }
}