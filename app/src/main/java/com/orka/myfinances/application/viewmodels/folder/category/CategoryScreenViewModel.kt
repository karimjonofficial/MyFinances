package com.orka.myfinances.application.viewmodels.folder.category

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.CategoryApiModel
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFul
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.category.CategoryScreenInteractor
import com.orka.myfinances.ui.screens.folder.category.CategoryScreenModel
import kotlinx.coroutines.flow.asStateFlow

class CategoryScreenViewModel(
    private val categoryId: Id,
    private val folderApi: FolderApi,
    private val loading: UiText,
    private val failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : StateFul<State<CategoryScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), CategoryScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            try {
                val category = folderApi.getById(categoryId.value)
                if (category != null && category is CategoryApiModel) {
                    val model = category.toScreenModel()
                    setState(State.Success(model))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun addProduct() {
        launch { navigator.navigateToAddProduct(categoryId) }
    }

    override fun receive() {
        launch { navigator.navigateToAddReceive(categoryId) }
    }

    override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                val category = folderApi.getById(categoryId.value)
                if (category != null && category is CategoryApiModel) {
                    val model = category.toScreenModel()
                    setState(State.Success(model))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}