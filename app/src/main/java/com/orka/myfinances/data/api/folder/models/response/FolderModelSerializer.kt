package com.orka.myfinances.data.api.folder.models.response

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object FolderModelSerializer : JsonContentPolymorphicSerializer<FolderApiModel>(FolderApiModel::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<FolderApiModel> {
        val isCatalog = element.jsonObject["is_catalog"]

        return if (!(isCatalog?.jsonPrimitive?.booleanOrNull ?: true)) {
            CategoryApiModel.serializer()
        } else {
            CatalogApiModel.serializer()
        }
    }
}