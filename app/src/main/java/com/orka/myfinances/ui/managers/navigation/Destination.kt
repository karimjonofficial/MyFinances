package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.product.Product

typealias CatalogModel = Catalog
typealias WarehouseModel = Warehouse
typealias ProductModel = Product

sealed class Destination(val hasNavBar: Boolean) {
    data class Home(val viewModel: Any) : Destination(true)
    data class Catalog(val catalog: CatalogModel, val viewModel: Any) : Destination(false)
    data class Warehouse(val warehouse: WarehouseModel, val viewModel: Any) : Destination(false)
    data object Profile : Destination(true)
    data class Basket(val viewModel: Any) : Destination(true)
    data object Notifications : Destination(false)
    data object Settings : Destination(true)
    data class Templates(val viewModel: Any) : Destination(false)
    data class Product(val product: ProductModel) : Destination(false)
    data class Clients(val viewModel: Any) : Destination(false)

    data class AddTemplate(
        val viewModel: Any,
        val types: List<String>
    ) : Destination(false)

    data class AddProduct(
        val warehouse: WarehouseModel,
        val viewModel: Any
    ) : Destination(false)
}