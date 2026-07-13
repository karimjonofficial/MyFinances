package com.orka.myfinances.data.api.folder.models.response

import kotlinx.serialization.Serializable

@Serializable
data class AddFolderApiResponse(
    val id: Int,
    val name: String
)