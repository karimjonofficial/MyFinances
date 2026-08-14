package com.orka.myfinances.lib.viewmodel.sourceful

import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.base.refreshable.RefreshableBaseViewModel
import com.orka.myfinances.ui.statuses.failure.EmptyDataFailure
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.logger.Logger

abstract class SourceFulViewModel<TData, TUi>(
    loadData: suspend () -> TData?,
    map: suspend (TData) -> TUi,
    exceptionMapper: ExceptionMapper<TUi> = NetworkExceptionMapper(),
    logger: Logger
) : RefreshableBaseViewModel<TUi>(
    produceInitialState = {
        val data = loadData()
        if(data != null)
            State.Success(map(data))
        else State.Failure(EmptyDataFailure)
    },
    exceptionMapper = exceptionMapper,
    logger = logger
)