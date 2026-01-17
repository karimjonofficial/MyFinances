package com.orka.myfinances.testFixtures.factories

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.factories.viewmodel.ViewModelProvider
import com.orka.myfinances.testFixtures.resources.viewModel

class SpyViewModelProvider : ViewModelProvider {
    var warehouseRequired = false

    override fun foldersViewModel(): Any {
        return "home"
    }

    override fun templatesViewModel(): Any {
        return "templates"
    }

    override fun addTemplateViewModel(): Any {
        return "add template"
    }

    override fun addProductViewModel(): Any {
        return "add product"
    }

    override fun warehouseViewModel(category: Category): Any {
        warehouseRequired = true
        return viewModel
    }

    override fun catalogViewModel(catalog: Catalog): Any {
        return "catalog"
    }

    override fun basketViewModel(): Any {
        return "basket"
    }

    override fun clientsViewModel(): Any {
        return "clients"
    }

    override fun saleViewModel(): Any {
        return "sale"
    }

    override fun receiveViewModel(): Any {
        return "receive"
    }

    override fun checkoutViewModel(): Any {
        return "checkout"
    }

    override fun addStockItemViewModel(): Any {
        return "add stock"
    }

    override fun notificationsViewModel(): Any {
        return "notifications"
    }

    override fun ordersViewModel(): Any {
        return "orders"
    }

    override fun debtsViewModel(): Any {
        return "debts"
    }
}