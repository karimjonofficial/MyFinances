package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse

typealias CatalogModel = Catalog
typealias WarehouseModel = Warehouse


//So all viewmodels are shipped via destinations
sealed class Destination(val hasNavBar: Boolean) {
    data class Home(val viewModel: Any) : Destination(true)
    data class Catalog(val catalog: CatalogModel) : Destination(false)
    data class Warehouse(val warehouse: WarehouseModel) : Destination(false)
    data object Profile : Destination(true)
    data object Notifications : Destination(false)
    data object Settings : Destination(true)
    data class Templates(val viewModel: Any) : Destination(false)

    data class AddTemplate(
        val viewModel: Any,
        val types: List<String>//TODO get rid of it until it does you
    ) : Destination(false)

    data class AddProduct(
        val warehouse: WarehouseModel,
        val viewModel: Any
    ) : Destination(false)
}