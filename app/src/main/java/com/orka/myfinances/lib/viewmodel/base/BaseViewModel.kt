package com.orka.myfinances.lib.viewmodel.base

import com.orka.myfinances.lib.ui.state.LoadingStatus
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.statuses.failure.ExecutedFromLoading
import com.orka.myfinances.lib.viewmodel.single.SingleStateViewModel
import com.orka.myfinances.logger.Logger

abstract class BaseViewModel<T>(
    protected val produceInitialState: suspend () -> State<T>,
    protected val exceptionMapper: ExceptionMapper<T> = ExceptionMapper.Default(),
    logger: Logger
) : SingleStateViewModel<State<T>>(
    initialState = State.Loading(LoadingStatus.Initial),
    logger = logger
) {
    fun initialize() {
        launch {
            tryToInitialize()
        }
    }

    protected suspend fun tryToInitialize() {
        try {
            val state = produceInitialState()
            setState(state)
        } catch (e: Exception) {
            setState(exceptionMapper.map(null, e))
        }
    }

    protected fun tryTransition(
        loadingState: (State<T>) -> State.Loading<T> = { State.Loading(value = state.value.value) },
        exceptionMapper: ExceptionMapper<T> = this.exceptionMapper,
        produceState: suspend (State<T>) -> State<T>
    ) {
        launch {
            val oldState = state.value
            if (oldState !is State.Loading) {
                try {
                    setState(loadingState(oldState))
                    val newState = produceState(oldState)
                    setState(newState)
                } catch (e: Exception) {
                    setState(exceptionMapper.map(oldState, e))
                }
            } else setState(
                State.Failure(
                    status = ExecutedFromLoading,
                    value = oldState.value
                )
            )
        }
    }
}
