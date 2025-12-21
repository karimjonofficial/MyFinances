package com.orka.myfinances.impl.factories.viewmodels

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.factories.viewmodel.CatalogScreenViewModelProvider
import com.orka.myfinances.factories.viewmodel.ViewModelProvider
import com.orka.myfinances.factories.viewmodel.WarehouseScreenViewModelProvider
import com.orka.myfinances.ui.navigation.OrdersScreenViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentViewModel
import com.orka.myfinances.ui.screens.clients.ClientsScreenViewModel
import com.orka.myfinances.ui.screens.products.add.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.checkout.CheckoutScreenViewModel
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.FoldersContentViewModel
import com.orka.myfinances.ui.screens.stock.AddStockItemScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

class ViewModelProviderImpl(
    private val addTemplateScreenViewModel: AddTemplateScreenViewModel,
    private val addProductScreenViewModel: AddProductScreenViewModel,
    private val basketContentViewModel: BasketContentViewModel,
    private val templatesScreenViewModel: TemplatesScreenViewModel,
    private val foldersContentViewModel: FoldersContentViewModel,
    private val clientsScreenViewModel: ClientsScreenViewModel,
    private val warehouseScreenViewModelProvider: WarehouseScreenViewModelProvider,
    private val catalogScreenViewModelProvider: CatalogScreenViewModelProvider,
    private val saleViewModel: SaleContentViewModel,
    private val receiveViewModel: ReceiveContentViewModel,
    private val checkoutViewModel: CheckoutScreenViewModel,
    private val addStockItemViewModel: AddStockItemScreenViewModel,
    private val ordersViewModel: OrdersScreenViewModel
) : ViewModelProvider {
    override fun addTemplateViewModel(): AddTemplateScreenViewModel {
        return addTemplateScreenViewModel
    }

    override fun templatesViewModel(): TemplatesScreenViewModel {
        templatesScreenViewModel.initialize()
        return templatesScreenViewModel
    }

    override fun foldersViewModel(): FoldersContentViewModel {
        foldersContentViewModel.initialize()
        return foldersContentViewModel
    }

    override fun addProductViewModel(): AddProductScreenViewModel {
        addProductScreenViewModel.initialize()
        return addProductScreenViewModel
    }

    override fun warehouseViewModel(warehouse: Warehouse): WarehouseScreenViewModel {
        val viewmodel = warehouseScreenViewModelProvider.warehouseViewModel(warehouse) as WarehouseScreenViewModel
        viewmodel.initialize()
        return viewmodel
    }

    override fun catalogViewModel(catalog: Catalog): CatalogScreenViewModel {
        val viewModel = catalogScreenViewModelProvider.catalogViewModel(catalog) as CatalogScreenViewModel
        viewModel.initialize()
        return viewModel
    }

    override fun basketViewModel(): BasketContentViewModel {
        basketContentViewModel.initialize()
        return basketContentViewModel
    }

    override fun clientsViewModel(): ClientsScreenViewModel {
        clientsScreenViewModel.initialize()
        return clientsScreenViewModel
    }

    override fun saleViewModel(): SaleContentViewModel {
        saleViewModel.initialize()
        return saleViewModel
    }

    override fun receiveViewModel(): ReceiveContentViewModel {
        receiveViewModel.initialize()
        return receiveViewModel
    }

    override fun checkoutViewModel(): CheckoutScreenViewModel {
        return checkoutViewModel
    }

    override fun addStockItemViewModel(): AddStockItemScreenViewModel {
        return addStockItemViewModel
    }

    override fun ordersViewModel(): OrdersScreenViewModel {
        ordersViewModel.initialize()
        return ordersViewModel
    }
}