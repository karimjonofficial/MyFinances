package com.orka.myfinances.lib.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class Manager : ViewModel() {
    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        callback: suspend () -> Unit
    ): Job {
        return viewModelScope.launch(
            context = context,
            start = start
        ) {
            callback.invoke()
        }
    }
}