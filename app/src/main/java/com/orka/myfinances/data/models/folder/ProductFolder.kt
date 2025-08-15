package com.orka.myfinances.data.models.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Product
import com.orka.myfinances.data.models.template.Template

data class ProductFolder(
    override val id: Id,
    override val name: String,
    val template: Template,
    val products: List<Product>?
) : Folder