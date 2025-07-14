package com.orka.myfinances.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class Manager(
    protected val defaultCoroutineContext: CoroutineContext
) : ViewModel() {

    protected fun launch(
        coroutineContext: CoroutineContext = defaultCoroutineContext,
        callback: suspend () -> Unit
    ): Job {
        return viewModelScope.launch(coroutineContext) {
            callback.invoke()
        }
    }
}