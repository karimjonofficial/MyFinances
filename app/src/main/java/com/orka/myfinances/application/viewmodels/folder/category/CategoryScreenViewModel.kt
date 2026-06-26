package com.orka.myfinances.application.viewmodels.folder.category

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.category.CategoryScreenInteractor
import com.orka.myfinances.ui.screens.folder.category.CategoryScreenModel
import kotlinx.coroutines.flow.asStateFlow

class CategoryScreenViewModel(
    private val categoryId: Id,
    private val repository: FolderRepository,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : BaseViewModel<CategoryScreenModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val category = repository.getById(categoryId)
        if (category != null && category is CategoryDto) {
            val model = category.toScreenModel()
            State.Success(model)
        } else null
    },
    logger = logger
), CategoryScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun addProduct() {
        launch { navigator.navigateToAddProduct(categoryId) }
    }

    override fun receive() {
        launch { navigator.navigateToAddReceive(categoryId) }
    }

    fun expose() {
        tryTransition { oldState ->
            if(oldState is State.Success)
                State.Success(oldState.value.copy(exposed = true))
            else State.Failure(failure, oldState.value)
        }
    }

    fun unExpose() {
        tryTransition { oldState ->
            if(oldState is State.Success)
                State.Success(oldState.value.copy(exposed = false))
            else State.Failure(failure, oldState.value)
        }
    }
}