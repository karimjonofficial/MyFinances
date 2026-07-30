package com.orka.myfinances.application.viewmodels.folder.category

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.ExecutedFromFailure
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.category.CategoryScreenInteractor
import com.orka.myfinances.ui.screens.folder.category.CategoryScreenModel
import kotlinx.coroutines.flow.asStateFlow

class CategoryScreenViewModel(
    categoryId: Id,
    getById: GetById<FolderDto>,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleByIdViewModel<CategoryDto, CategoryScreenModel>(
    id = categoryId,
    get = { getById.getById(categoryId) as CategoryDto? },
    map = { it.toScreenModel() },
    logger = logger
), CategoryScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun addProduct() {
        launch { navigator.navigateToAddProduct(id) }
    }

    override fun receive() {
        launch { navigator.navigateToAddReceive(id) }
    }

    fun expose() {
        tryTransition { oldState ->
            if(oldState is State.Success)
                State.Success(oldState.value.copy(exposed = true))
            else State.Failure(ExecutedFromFailure, oldState.value)
        }
    }

    fun unExpose() {
        tryTransition { oldState ->
            if(oldState is State.Success)
                State.Success(oldState.value.copy(exposed = false))
            else State.Failure(ExecutedFromFailure, oldState.value)
        }
    }
}