package com.orka.myfinances.lib.viewmodel.sourceful.list.format

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.SourceFulViewModel
import com.orka.myfinances.logger.Logger

abstract class FormatListViewModel<TData, TUi>(
    private val get: Get<TData>,
    private val map: (TData) -> TUi,
    exceptionMapper: ExceptionMapper<List<TUi>> = NetworkExceptionMapper(),
    logger: Logger
) : SourceFulViewModel<List<TData>, List<TUi>>(
    loadData = { get.getAll() },
    map = { list -> list.map(map) },
    exceptionMapper = exceptionMapper,
    logger = logger
)