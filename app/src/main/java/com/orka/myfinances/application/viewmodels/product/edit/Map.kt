package com.orka.myfinances.application.viewmodels.product.edit

import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.api.title.models.response.PropertyApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.types.Range
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.fixtures.resources.Types
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryItemModel

fun ProductTitleApiModel.toEditorModel(categories: List<CategoryItemModel>): AddProductTitleScreenModel {
    return AddProductTitleScreenModel(
        categories = categories,
        categoryId = Id(category),
        initialName = name,
        initialPrice = defaultPrice.toInt(),
        initialSalePrice = defaultSalePrice.toInt(),
        initialDescription = description,
        initialProperties = properties.map(PropertyApiModel::toPropertyModel),
        isEditMode = true
    )
}

fun PropertyApiModel.toPropertyModel(): PropertyModel<*> {
    return PropertyModel(
        fieldId = Id(field.id),
        value = when (field.type) {
            Types.TEXT -> value
            Types.NUMBER -> value.toInt()
            Types.BOOLEAN -> value.toBoolean()
            Types.RANGE -> value.toRange()
            else -> value
        }
    )
}

private fun String.toRange(): Range {
    val regex = Regex("""Range\(min=(\d+), max=(\d+)\)""")
    val match = regex.matchEntire(this)

    return if (match != null) {
        Range(
            min = match.groupValues[1].toInt(),
            max = match.groupValues[2].toInt()
        )
    } else Range(0, 0)
}
