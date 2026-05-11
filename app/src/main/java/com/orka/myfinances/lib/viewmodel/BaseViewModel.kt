package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class BaseViewModel<T>(
    private val produceSuccess: suspend () -> State.Success<T>?,
    protected val loading: UiText,
    protected val failure: UiText,
    logger: Logger
) : StateFulViewModel<State<T>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    final override fun initialize() {
        launch {
            try {
                val success = produceSuccess()
                if (success != null) {
                    setState(success)
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    final override fun refresh() {
        tryTransition { oldState ->
            val success = produceSuccess()
            success ?: State.Failure(failure, oldState.value)
        }
    }

    protected fun tryTransition(produceState: suspend (State<T>) -> State<T>) {
        launch {
            val oldState = state.value
            if (oldState !is State.Loading) {
                try {
                    setState(State.Loading(loading, oldState.value))
                    val newState = produceState(oldState)
                    setState(newState)
                } catch (e: Exception) {
                    setState(State.Failure(UiText.Str(e.message.toString()), oldState.value))
                }
            } else setState(
                State.Failure(
                    UiText.Str("Refreshed when state was Loading"),
                    oldState.value
                )
            )
        }
    }
}