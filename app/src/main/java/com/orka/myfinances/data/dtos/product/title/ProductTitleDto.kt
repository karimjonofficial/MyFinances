package com.orka.myfinances.data.dtos.product.title

import com.orka.myfinances.data.dtos.template.TemplateFieldDto
import kotlin.time.Instant

data class ProductTitleDto(
    val id: Int,
    val category: Int,
    val name: String,
    val properties: List<PropertyDto>,
    val defaultPrice: Long,
    val defaultSalePrice: Long,
    val defaultExposedPrice: Long,
    val createdAt: Instant,
    val modifiedAt: Instant,
    val description: String? = null
)

data class PropertyDto(
    val id: Int,
    val field: TemplateFieldDto,
    val value: String
)
