package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class FormatListViewModel<T, U>(
    loading: UiText,
    failure: UiText,
    private val get: Get<T>,
    private val map: (T) -> U,
    logger: Logger
) : BaseViewModel<List<U>>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val response = get.getAll()
        if(response != null) {
            State.Success(response.map { map(it) })
        } else null
    },
    logger = logger
)