package com.orka.myfinances.data.api.sale.models.response

import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.api.user.UserApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class SaleApiModel(
    val id: Int,
    val client: ClientApiModel,
    val user: UserApiModel,
    val items: List<SaleItemApiModel>,
    @SerialName("created_at") val dateTime: Instant,
    val price: Long,
    val description: String? = null,
)