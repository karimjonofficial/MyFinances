package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.ui.screens.home.viewmodel.profile.UserApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class OrderApiModel(
    val id: Int,
    val user: UserApiModel,
    val client: ClientApiModel,
    val branch: OfficeApiModel,
    val price: Long,
    @SerialName("end_date_time") val endDateTime: String? = null,
    val completed: Boolean,
    val description: String? = null,
    val items: List<OrderItemApiModel>,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant
)

@Serializable
data class OrderItemApiModel(
    val id: Int,
    val order: Int,
    val product: OrderProductApiModel,
    val amount: Long,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant
)

@Serializable
data class OrderProductApiModel(
    val id: Int,
    @SerialName("product_title") val productTitle: Int,
    val branch: Int,
    val price: Long,
    @SerialName("sale_price") val salePrice: Long,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant
)
