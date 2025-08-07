package com.orka.myfinances.data.models

sealed interface Catalog {
    val id: Id

    data class CatalogFolder(
        override val id: Id,
        val name: String,
        val catalogs: List<Catalog>
    ) : Catalog

    data class ProductFolder(
        override val id: Id,
        val name: String,
        val template: ProductTemplate,
        val products: List<Product>
    ) : Catalog
}