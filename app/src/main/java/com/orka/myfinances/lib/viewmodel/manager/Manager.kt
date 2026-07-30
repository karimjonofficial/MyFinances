package com.orka.myfinances.lib.viewmodel.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orka.myfinances.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class Manager(
    protected val logger: Logger
) : ViewModel() {
    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(
            context = context,
            start = start,
            block = block
        )
    }

    override fun onCleared() {
        logger.log(this.javaClass.name, "OnCleared")
    }
}