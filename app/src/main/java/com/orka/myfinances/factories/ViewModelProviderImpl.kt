package com.orka.myfinances.factories

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.factories.viewmodel.CatalogScreenViewModelProvider
import com.orka.myfinances.factories.viewmodel.ViewModelProvider
import com.orka.myfinances.factories.viewmodel.WarehouseScreenViewModelProvider
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

class ViewModelProviderImpl(
    private val addTemplateScreenViewModel: AddTemplateScreenViewModel,
    private val addProductScreenViewModel: AddProductScreenViewModel,
    private val templatesScreenViewModel: TemplatesScreenViewModel,
    private val homeScreenViewModel: HomeScreenViewModel,
    private val warehouseScreenViewModelProvider: WarehouseScreenViewModelProvider,
    private val catalogScreenViewModelProvider: CatalogScreenViewModelProvider
) : ViewModelProvider {
    override fun addTemplateViewModel(): AddTemplateScreenViewModel {
        return addTemplateScreenViewModel
    }

    override fun templatesViewModel(): TemplatesScreenViewModel {
        templatesScreenViewModel.initialize()
        return templatesScreenViewModel
    }

    override fun homeViewModel(): HomeScreenViewModel {
        return homeScreenViewModel
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
}