package com.orka.myfinances.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class Manager(protected val viewModelScope: CoroutineScope) {

    protected fun launch(
        coroutineContext: CoroutineContext = viewModelScope.coroutineContext,
        callback: suspend () -> Unit
    ): Job {
        return viewModelScope.launch(coroutineContext) {
            callback.invoke()
        }
    }

    protected fun newScope(): CoroutineScope {
        return CoroutineScope(viewModelScope.coroutineContext + SupervisorJob())
    }
}