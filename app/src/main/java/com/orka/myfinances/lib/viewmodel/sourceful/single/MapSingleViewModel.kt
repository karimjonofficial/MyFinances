package com.orka.myfinances.lib.viewmodel.sourceful.single

import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.SourceFulViewModel
import com.orka.myfinances.logger.Logger

abstract class MapSingleViewModel<TData, TUi>(
    protected val get: suspend () -> TData?,
    protected val map: (TData) -> TUi,
    exceptionMapper: ExceptionMapper<TUi> = NetworkExceptionMapper(),
    logger: Logger
) : SourceFulViewModel<TData, TUi>(
    loadData = { get() },
    map = map,
    exceptionMapper = exceptionMapper,
    logger = logger
)