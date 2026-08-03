package com.orka.myfinances.lib.viewmodel.sourceful.list.map

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.ui.viewmodel.Searchable
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.ui.statuses.failure.ExecutedFromFailure
import com.orka.myfinances.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow

abstract class SearchableMapListViewModel<TData, TUi>(
    get: Get<TData>,
    map: suspend (TData) -> TUi,
    groupBy: (TData) -> String,
    private val match: (query: String, TUi) -> Boolean,
    exceptionMapper: ExceptionMapper<Map<String, List<TUi>>> = NetworkExceptionMapper(),
    logger: Logger
) : MapListViewModel<TData, TUi>(
    get = get,
    map = map,
    groupBy = groupBy,
    exceptionMapper = exceptionMapper,
    logger = logger
), Searchable {
    protected val cachedState = MutableStateFlow<Map<String, List<TUi>>>(emptyMap())
    //TODO refresh and initialize should reset the cache

    final override fun search(query: String) {
        tryTransition { oldState ->
            if(oldState !is State.Success) {
                State.Failure(ExecutedFromFailure, oldState.value)
            } else {
                if(query.isNotBlank()) {
                    cachedState.value = oldState.value
                    val result = oldState.value.mapValues { (_, items) ->
                        items.filter { match(query, it) }
                    }.filter { it.value.isNotEmpty() }

                    State.Success(result)
                } else {
                    val cachedStateValue = cachedState.value
                    cachedState.value = emptyMap()
                    State.Success(cachedStateValue)
                }
            }
        }
    }

    final override fun resetSearch() {
        tryTransition { oldState ->
            if(oldState !is State.Success) {
                State.Failure(ExecutedFromFailure, oldState.value)
            } else {
                cachedState.value = emptyMap()
                State.Success(oldState.value)
            }
        }
    }
}