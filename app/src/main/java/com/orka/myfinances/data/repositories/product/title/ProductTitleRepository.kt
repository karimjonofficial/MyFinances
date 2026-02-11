package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.fixtures.resources.models.product.productTitles
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.data.repositories.Generator
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ProductTitleRepository(
    private val categoryRepository: GetById<Category>,
    private val fieldRepository: GetById<TemplateField>,
    private val generator: Generator<Id>
) : MockGetByIdRepository<ProductTitle>,
    MockGetByParameterRepository<ProductTitle, Category>,
    MockAddRepository<ProductTitle, AddProductTitleRequest>,
    MockGetRepository<ProductTitle>, SetProductTitlePrice {
    private val flow = MutableSharedFlow<ProductTitleEvent>()
    val events: Flow<ProductTitleEvent> = flow

    override val items = productTitles.toMutableList()

    private suspend fun emit(title: ProductTitle) {
        flow.emit(ProductTitleEvent(title.category.id))
    }

    override suspend fun AddProductTitleRequest.map(): ProductTitle {
        val category = categoryRepository.getById(categoryId)!!
        return ProductTitle(
            id = generator.generate(),
            name = name,
            defaultPrice = price,
            defaultSalePrice = salePrice,
            dateTime = now(),
            category = category,
            properties = properties.map {
                Property(
                    id = generator.generate(),
                    type = fieldRepository.getById(it.fieldId)!!,
                    value = it.value
                )
            },
            description = description
        )
    }

    override suspend fun setPrice(id: Id, price: Int, salePrice: Int): ProductTitle {
        delay(duration)
        val index = items.indexOfFirst { it.id == id }
        val t = items[index]
        val new = t.copy(defaultPrice = price, defaultSalePrice = salePrice)
        items[index] = new
        emit(new)
        return new
    }

    override suspend fun afterAdd(item: ProductTitle) {
        emit(item)
    }

    override suspend fun List<ProductTitle>.filter(parameter: Category): List<ProductTitle> {
        return this.filter { it.category == parameter }
    }

    override suspend fun List<ProductTitle>.find(id: Id): ProductTitle? {
        return this.find { it.id == id }
    }
}