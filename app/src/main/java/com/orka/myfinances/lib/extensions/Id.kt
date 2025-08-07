package com.orka.myfinances.lib.extensions

import com.orka.myfinances.data.models.Id

fun Int.toId(): Id {
    return Id(this)
}