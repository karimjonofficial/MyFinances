package com.orka.myfinances.lib.viewmodel.sourceful.single

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.SourceFulViewModel
import com.orka.myfinances.logger.Logger

abstract class MapSingleByIdViewModel<TData, TUi>(
    protected val id: Id,
    protected val get: GetById<TData>,
    protected val map: (TData) -> TUi,
    exceptionMapper: ExceptionMapper<TUi> = NetworkExceptionMapper(),
    logger: Logger
) : SourceFulViewModel<TData, TUi>(
    loadData = { get.getById(id) },
    map = map,
    exceptionMapper = exceptionMapper,
    logger = logger
)