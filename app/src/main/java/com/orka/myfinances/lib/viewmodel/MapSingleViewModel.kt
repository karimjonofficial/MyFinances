package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class MapSingleViewModel<TApi, TUi>(
    private val id: Id,
    private val get: GetById<TApi>,
    private val map: (TApi) -> TUi,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : StateFulViewModel<State<TUi>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    final override fun initialize() {
        launch {
            try {
                val response = get.getById(id)
                if (response != null) {
                    setState(State.Success(map(response)))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    final override fun refresh() {
        launch {
            val oldValue = state.value.value
            try {
                setState(State.Loading(loading, oldValue))
                val response = get.getById(id)
                if (response != null) {
                    setState(State.Success(map(response)))
                } else setState(State.Failure(failure, oldValue))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString()), oldValue))
            }
        }
    }
}