package com.orka.myfinances.ui.screens.home.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.Text
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class ProfileContentViewModel(
    loading: Text,
    failure: Text,
    repository: Get<Office>,
    logger: Logger
) : ListViewModel<Office>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger
)