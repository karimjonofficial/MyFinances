package com.orka.myfinances.factories

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.data.repositories.debt.DebtRepository
import com.orka.myfinances.data.repositories.folder.CategoryRepository
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.notification.NotificationRepository
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.receive.ReceiveRepository
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.checkout.CheckoutScreenViewModel
import com.orka.myfinances.ui.screens.clients.ClientsScreenViewModel
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtScreenViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentSingleStateViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.FoldersContentViewModel
import com.orka.myfinances.ui.screens.notification.NotificationScreenViewModel
import com.orka.myfinances.ui.screens.order.OrdersScreenViewModel
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.screens.stock.AddReceiveScreenViewModel
import com.orka.myfinances.ui.screens.templates.viewmodel.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

class Factory(
    private val folderRepository: FolderRepository,
    private val templateRepository: TemplateRepository,
    private val productRepository: ProductRepository,
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
            getRepository = folderRepository,
            addRepository = folderRepository,
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

    fun addProductViewModel(): AddProductScreenViewModel {
        return AddProductScreenViewModel(
            productRepository = productRepository,
            categoryRepository = categoryRepository,
            logger = logger
        )
    }

    fun warehouseViewModel(category: Category): WarehouseScreenViewModel {
        return WarehouseScreenViewModel(
            category = category,
            productRepository = productRepository,
            stockRepository = stockRepository,
            basketRepository = basketRepository,
            events = productRepository.events,
            logger = logger
        )
    }

    fun catalogViewModel(catalog: Catalog): CatalogScreenViewModel {
        return CatalogScreenViewModel(
            catalog = catalog,
            repository = folderRepository,
            logger = logger
        )
    }

    fun basketViewModel(): BasketContentSingleStateViewModel {
        return BasketContentSingleStateViewModel(
            repository = basketRepository,
            logger = logger
        )
    }

    fun clientsViewModel(): ClientsScreenViewModel {
        return ClientsScreenViewModel(
            getRepository = clientRepository,
            addRepository = clientRepository,
            loading = R.string.loading,
            failure = R.string.failure,
            logger = logger
        )
    }

    fun saleViewModel(): SaleContentViewModel {
        return SaleContentViewModel(
            loading = R.string.loading,
            failure = R.string.failure,
            repository = saleRepository,
            logger = logger
        )
    }

    fun receiveViewModel(): ReceiveContentViewModel {
        return ReceiveContentViewModel(
            repository = receiveRepository,
            loading = R.string.loading,
            failure = R.string.failure,
            logger = logger
        )
    }

    fun checkoutViewModel(): CheckoutScreenViewModel {
        return CheckoutScreenViewModel(
            saleRepository = saleRepository,
            orderRepository = orderRepository,
            basketRepository = basketRepository,
            clientRepository = clientRepository,
            logger = logger
        )
    }

    fun addReceiveViewModel(): AddReceiveScreenViewModel {
        return AddReceiveScreenViewModel(
            repository = receiveRepository
        )
    }

    fun notificationsViewModel(): NotificationScreenViewModel {
        return NotificationScreenViewModel(
            repository = notificationRepository,
            logger = logger
        )
    }

    fun ordersViewModel(): OrdersScreenViewModel {
        return OrdersScreenViewModel(
            repository = orderRepository,
            loading = R.string.loading,
            failure = R.string.failure,
            logger = logger
        )
    }

    fun debtsViewModel(): DebtScreenViewModel {
        return DebtScreenViewModel(
            debtRepository = debtRepository,
            addRepository = debtRepository,
            clientRepository = clientRepository,
            logger = logger
        )
    }
}