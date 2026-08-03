package com.orka.myfinances.lib.viewmodel.base.refreshable

import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.statuses.loading.Refresh
import com.orka.myfinances.lib.viewmodel.base.BaseViewModel
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.logger.Logger

abstract class RefreshableBaseViewModel<T>(
    produceInitialState: suspend () -> State<T>,
    exceptionMapper: ExceptionMapper<T> = ExceptionMapper.Default(),
    logger: Logger
) : BaseViewModel<T>(
    produceInitialState,
    exceptionMapper = exceptionMapper,
    logger = logger
), Refreshable {
    final override fun refresh() {
        launch {
            setState(State.Loading(status = Refresh, value = state.value.value))
            tryToInitialize()
        }
    }
}