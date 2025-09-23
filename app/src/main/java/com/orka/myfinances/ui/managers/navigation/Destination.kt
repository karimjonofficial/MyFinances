package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel

typealias CatalogModel = Catalog
typealias WarehouseModel = Warehouse


//So all viewmodels are shipped via destinations
sealed class Destination(val hasNavBar: Boolean) {
    data class Home(val viewModel: HomeScreenViewModel) : Destination(true)
    data class Catalog(val catalog: CatalogModel) : Destination(false)
    data class Warehouse(val warehouse: WarehouseModel) : Destination(false)
    data object Profile : Destination(true)
    data object Notifications : Destination(false)
    data object Settings : Destination(true)
    data class Templates(val viewModel: TemplatesScreenViewModel) : Destination(false)

    data class AddTemplate(
        val viewModel: AddTemplateScreenViewModel,
        val types: List<String>//TODO get rid of it until it does you
    ) : Destination(false)

    data class AddProduct(
        val warehouse: WarehouseModel,
        val viewModel: AddProductScreenViewModel
    ) : Destination(false)
}