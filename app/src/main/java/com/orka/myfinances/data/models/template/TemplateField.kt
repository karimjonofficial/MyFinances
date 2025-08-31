package com.orka.myfinances.data.models.template

import com.orka.myfinances.data.models.Id
import kotlin.reflect.KClass

data class TemplateField(
    val id: Id,
    val name: String,
    val type: KClass<*>
)