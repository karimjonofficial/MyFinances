package com.orka.myfinances.lib.viewmodel.sourceful.list.format

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.SourceFulViewModel
import com.orka.myfinances.logger.Logger

abstract class FormatListViewModel<T, U>(
    private val get: Get<T>,
    private val map: (T) -> U,
    exceptionMapper: ExceptionMapper<List<U>> = NetworkExceptionMapper(),
    logger: Logger
) : SourceFulViewModel<List<T>, List<U>>(
    loadData = { get.getAll() },
    map = { list -> list.map(map) },
    exceptionMapper = exceptionMapper,
    logger = logger
)