package com.orka.myfinances.factories

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
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
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.checkout.CheckoutScreenViewModel
import com.orka.myfinances.ui.screens.clients.ClientsScreenViewModel
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtScreenViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.FoldersContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.ProfileContentViewModel
import com.orka.myfinances.ui.screens.notification.NotificationScreenViewModel
import com.orka.myfinances.ui.screens.order.OrdersScreenViewModel
import com.orka.myfinances.ui.screens.products.ProductTitleScreenViewModel
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductTitleScreenViewModel
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenViewModel
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
    private val logger: Logger
) {
    fun foldersViewModel(): FoldersContentViewModel {
        return FoldersContentViewModel(
            get = folderRepository,
            add = folderRepository,
            templateRepository = templateRepository,
            logger = logger
        )
    }

    fun templatesViewModel(): TemplatesScreenViewModel {
        return TemplatesScreenViewModel(
            repository = templateRepository,
            events = templateRepository.events,
            logger = logger
        )
    }

    fun addTemplateViewModel(): AddTemplateScreenViewModel {
        return AddTemplateScreenViewModel(
            repository = templateRepository
        )
    }

    fun addProductViewModel(): AddProductTitleScreenViewModel {
        return AddProductTitleScreenViewModel(
            productTitleRepository = productTitleRepository,
            categoryRepository = categoryRepository,
            logger = logger
        )
    }

    fun warehouseViewModel(category: Category): WarehouseScreenViewModel {
        return WarehouseScreenViewModel(
            category = category,
            productTitleRepository = productTitleRepository,
            stockRepository = stockRepository,
            basketRepository = basketRepository,
            productTitleEvents = productTitleRepository.events,
            stockEvents = stockRepository.events,
            logger = logger
        )
    }

    fun catalogViewModel(catalog: Catalog): CatalogScreenViewModel {
        return CatalogScreenViewModel(
            catalog = catalog,
            repository = folderRepository,
            addRepository = folderRepository,
            templateRepository = templateRepository,
            events = folderRepository.events,
            logger = logger
        )
    }

    fun basketViewModel(): BasketContentViewModel {
        return BasketContentViewModel(
            repository = basketRepository,
            logger = logger
        )
    }

    fun clientsViewModel(): ClientsScreenViewModel {
        return ClientsScreenViewModel(
            get = clientRepository,
            add = clientRepository,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            logger = logger
        )
    }

    fun saleViewModel(): SaleContentViewModel {
        return SaleContentViewModel(
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            repository = saleRepository,
            events = saleRepository.events,
            logger = logger
        )
    }

    fun receiveViewModel(): ReceiveContentViewModel {
        return ReceiveContentViewModel(
            repository = receiveRepository,
            events = receiveRepository.events,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            logger = logger
        )
    }

    fun checkoutViewModel(): CheckoutScreenViewModel {
        return CheckoutScreenViewModel(
            saleRepository = saleRepository,
            orderRepository = orderRepository,
            basketRepository = basketRepository,
            clientRepository = clientRepository,
            logger = logger,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
        )
    }

    fun addReceiveViewModel(category: Category): AddReceiveScreenViewModel {
        return AddReceiveScreenViewModel(
            titleRepository = productTitleRepository,
            receiveRepository = receiveRepository,
            category = category,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            logger = logger
        )
    }

    fun notificationsViewModel(): NotificationScreenViewModel {
        return NotificationScreenViewModel(
            repository = notificationRepository,
            logger = logger,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
        )
    }

    fun ordersViewModel(): OrdersScreenViewModel {
        return OrdersScreenViewModel(
            repository = orderRepository,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            logger = logger
        )
    }

    fun debtsViewModel(): DebtScreenViewModel {
        return DebtScreenViewModel(
            debtRepository = debtRepository,
            add = debtRepository,
            clientRepository = clientRepository,
            logger = logger,
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
        )
    }

    fun profileViewModel(): ProfileContentViewModel {
        return ProfileContentViewModel(
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure),
            repository = officeApi,
            logger = logger
        )
    }

    fun productTitleViewModel(productTitle: ProductTitle): ProductTitleScreenViewModel {
        return ProductTitleScreenViewModel(
            productTitle = productTitle,
            repository = receiveRepository
        )
    }
}