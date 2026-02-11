package com.orka.myfinances.lib.ui.models

sealed interface UiText {
    data class Str(val value: String) : UiText
    data class Res(val id: Int) : UiText
}