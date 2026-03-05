package com.orka.myfinances.data.api.folder

import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlin.collections.contains

object FolderModelSerializer : JsonContentPolymorphicSerializer<FolderModel>(FolderModel::class) {
    override fun selectDeserializer(element: JsonElement) = when {
        "template" in element.jsonObject -> CategoryModel.serializer()
        else -> CatalogModel.serializer()
    }
}