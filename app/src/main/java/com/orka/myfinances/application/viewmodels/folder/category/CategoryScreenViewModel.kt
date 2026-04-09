package com.orka.myfinances.application.viewmodels.folder.category

import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.models.Id
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
    private val folderApi: FolderApi,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : BaseViewModel<CategoryScreenModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val category = folderApi.getById(categoryId.value)
        if (category != null && category is CategoryApiModel) {
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
}