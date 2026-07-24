package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.logger.Logger

abstract class BaseViewModel<T>(
    private val produceInitialState: suspend () -> State<T>?,
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
                val state = produceInitialState()
                if (state != null) {
                    setState(state)
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    final override fun refresh() {
        tryTransition { oldState ->
            val s = produceInitialState()
            s ?: State.Failure(failure, oldState.value)
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