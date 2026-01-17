package com.orka.myfinances.lib.ui.models

sealed interface Text {
    data class Str(val value: String) : Text
    data class Res(val id: Int) : Text
}