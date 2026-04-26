package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class MapSingleViewModel<TApi, TUi>(
    protected val id: Id,
    private val get: GetById<TApi>,
    private val map: (TApi) -> TUi,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<TUi>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val response = get.getById(id)
        if (response != null) {
            State.Success(map(response))
        } else null
    },
    logger = logger
)