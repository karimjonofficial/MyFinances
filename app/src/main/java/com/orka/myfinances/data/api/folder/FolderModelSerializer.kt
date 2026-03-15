package com.orka.myfinances.data.api.folder

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.jsonObject

object FolderModelSerializer : JsonContentPolymorphicSerializer<FolderApiModel>(FolderApiModel::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<FolderApiModel> {
        val template = element.jsonObject["template"]

        return if (template != null && template !is JsonNull) {
            CategoryApiModel.serializer()
        } else {
            CatalogApiModel.serializer()
        }
    }
}