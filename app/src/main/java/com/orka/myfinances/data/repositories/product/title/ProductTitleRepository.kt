package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.product.Property
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.product.productTitles
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ProductTitleRepository(
    private val categoryRepository: GetById<Category>,
    private val fieldRepository: GetById<TemplateField>
) : MockGetByIdRepository<ProductTitle>,
    MockGetByParameterRepository<ProductTitle, Category>,
    MockAddRepository<ProductTitle, AddProductTitleRequest>,
    MockGetRepository<ProductTitle> {
    private val flow = MutableSharedFlow<ProductTitleEvent>()
    val events: Flow<ProductTitleEvent> = flow

    override val items = productTitles.toMutableList()

    override suspend fun AddProductTitleRequest.map(): ProductTitle {
        val category = categoryRepository.getById(categoryId)!!
        return ProductTitle(
            id = id1,
            name = name,
            defaultPrice = price,
            defaultSalePrice = salePrice,
            dateTime = now(),
            category = category,
            properties = properties.map {
                Property(
                    id = id1,
                    type = fieldRepository.getById(it.fieldId)!!,
                    value = it.value
                )
            },
            description = description
        )
    }

    override suspend fun afterAdd(item: ProductTitle) {
        flow.emit(ProductTitleEvent(item.category.id))
    }

    override suspend fun List<ProductTitle>.filter(parameter: Category): List<ProductTitle> {
        return this.filter { it.category == parameter }
    }

    override suspend fun List<ProductTitle>.find(id: Id): ProductTitle? {
        return this.find { it.id == id }
    }
}