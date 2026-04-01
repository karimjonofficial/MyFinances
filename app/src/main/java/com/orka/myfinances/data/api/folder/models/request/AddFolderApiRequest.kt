package com.orka.myfinances.data.api.folder.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddFolderApiRequest(
    val name: String,
    val type: String,
    @SerialName("branch") val officeId: Int,
    @SerialName("template") val templateId: Int? = null,
    @SerialName("parent") val parentId: Int? = null
)