package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class MapListViewModel<T, K>(
    loading: UiText,
    failure: UiText,
    private val get: Get<T>,
    private val map: (List<T>) -> Map<String, List<K>>,
    logger: Logger
) : BaseViewModel<Map<String, List<K>>>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val data = get.getAll()
        if (data != null) {
            val groupedData = map(data)
            State.Success(groupedData)
        } else null
    },
    logger = logger
)