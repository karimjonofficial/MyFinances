package com.orka.myfinances.lib.viewmodel

import android.util.Log
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

    override fun onCleared() {
        super.onCleared()
        Log.d(this.javaClass.name, "OnCleared")
    }
}