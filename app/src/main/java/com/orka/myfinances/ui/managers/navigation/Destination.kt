package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.template.TemplateScreenViewModel

typealias CatalogModel = Catalog
typealias ProductFolderModel = ProductFolder


//So all viewmodels are shipped via destinations
sealed interface Destination {
    data class Home(val viewModel: HomeScreenViewModel) : Destination
    data class Catalog(val catalog: CatalogModel) : Destination
    data class ProductFolder(val productFolder: ProductFolderModel) : Destination
    data object Profile : Destination
    data object Notifications : Destination

    data class AddTemplate(
        val viewModel: TemplateScreenViewModel,
        val types: List<String>//TODO get rid of it until it does you
    ) : Destination
}