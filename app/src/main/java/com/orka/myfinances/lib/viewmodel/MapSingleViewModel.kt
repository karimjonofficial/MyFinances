package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.logger.Logger

abstract class MapSingleViewModel<TData, TUi>(
    protected val id: Id,
    protected val get: GetById<TData>,
    protected val map: (TData) -> TUi,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<TUi>(
    loading = loading,
    failure = failure,
    produceModel = {
        val response = get.getById(id)
        if (response != null) {
            map(response)
        } else null
    },
    logger = logger
)