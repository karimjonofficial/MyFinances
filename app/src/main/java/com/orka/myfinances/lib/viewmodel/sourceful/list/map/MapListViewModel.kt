package com.orka.myfinances.lib.viewmodel.sourceful.list.map

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.SourceFulViewModel
import com.orka.myfinances.logger.Logger

abstract class MapListViewModel<TData, TUi>(
    private val get: Get<TData>,
    map: suspend (TData) -> TUi,
    groupBy: (TData) -> String,
    exceptionMapper: ExceptionMapper<Map<String, List<TUi>>> = NetworkExceptionMapper(),
    logger: Logger
) : SourceFulViewModel<List<TData>, Map<String, List<TUi>>>(
    loadData = { get.getAll() },
    map = { list ->
        list
            .groupBy(groupBy)
            .mapValues { (_, v) -> v.map { map(it) } }
    },
    exceptionMapper = exceptionMapper,
    logger = logger
)