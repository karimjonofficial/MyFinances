package com.orka.myfinances.fixtures.factories

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.factories.viewmodel.ViewModelProvider
import com.orka.myfinances.testLib.viewModel

class SpyViewModelProvider : ViewModelProvider {
    var warehouseRequired = false

    override fun homeViewModel(): Any {
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

    override fun warehouseViewModel(warehouse: Warehouse): Any {
        warehouseRequired = true
        return viewModel
    }

    override fun catalogViewModel(catalog: Catalog): Any {
        return "catalog"
    }
}