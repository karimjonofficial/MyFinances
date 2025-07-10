package com.orka.myfinances.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class Manager(private val context: CoroutineContext) : ViewModel() {

    protected fun launch(
        coroutineContext: CoroutineContext = context,
        callback: suspend () -> Unit
    ): Job {
        return viewModelScope.launch(coroutineContext) {
            callback.invoke()
        }
    }
}