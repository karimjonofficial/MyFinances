package com.orka.myfinances.application.viewmodels.folder.home

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.folder.toUiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.GetTop
import com.orka.myfinances.data.repositories.preferences.categories.PinnedCategoriesRepository
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentModel
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FoldersContentViewModel(
    private val getTop: GetTop,
    private val addFolder: Add<Unit, AddFolderRequest>,
    private val addTitle: Add<Id, AddProductTitleRequest>,
    private val addReceive: Insert<AddReceiveRequest>,
    private val pinnedCategoriesRepository: PinnedCategoriesRepository,
    private val getDefaultCategory: GetDefaultCategory,
    private val navigator: Navigator,
    folderFlow: Flow<FolderEvent>,
    defaultsFlow: Flow<DefaultsEvent>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<FoldersContentModel>(
    loading = loading,
    failure = failure,
    produceInitialState = {
        val folders = getTop.getTop(null)
        val categories = pinnedCategoriesRepository.getAll(null)

        if (folders != null) {
            val isDefaultSet = getDefaultCategory.getDefaultCategoryId()

            State.Success(
                FoldersContentModel(
                    folders = folders.map { it.toUiModel() },
                    isDefaultCategorySet = isDefaultSet != null,
                    pinnedCategories = categories
                )
            )
        } else null
    },
    logger = logger
), FoldersContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        folderFlow.onEach {
            if (it.catalogId == null) initialize()
        }.launchIn(viewModelScope)

        defaultsFlow.onEach {
            if (it is DefaultsEvent.Category) initialize()
        }.launchIn(viewModelScope)
    }

    override fun addFolder(name: String, type: String, templateId: Id?) {
        launch {
            setState(State.Loading(loading))
            val request = AddFolderRequest(name, type, templateId, null)
            addFolder.add(request)
        }
    }

    override fun select(folder: FolderUiModel) {
        launch {
            when (folder.isCatalog) {
                true -> navigator.navigateToCatalog(folder.id)
                false -> navigator.navigateToCategory(folder.id)
            }
        }
    }

    override fun navigateToNotifications() {
        launch {
            navigator.navigateToNotifications()
        }
    }

    override fun navigateToSearch() {
        launch {
            navigator.navigateToSearch()
        }
    }

    override fun navigateToAddTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }

    fun addProduct(name: String, price: Int, salePrice: Int, exposedPrice: Int, amount: Int) {
        tryTransition { oldState ->
            val id = getDefaultCategory.getDefaultCategoryId()
            if(id != null) {
                val titleRequest = AddProductTitleRequest(
                    categoryId = id,
                    name = name,
                    price = price,
                    salePrice = salePrice,
                    exposedPrice = exposedPrice,
                    properties = emptyList()
                )
                val r1 = addTitle.add(titleRequest)
                if(r1 != null) {
                    val receiveRequest = AddReceiveRequest(
                        categoryId = id,
                        items = listOf(
                            AddReceiveRequestItem(
                                r1,
                                price,
                                salePrice,
                                exposedPrice,
                                amount
                            )
                        ),
                        price = price * amount
                    )
                    val r2 = addReceive.insert(receiveRequest)
                    if(r2) oldState
                    else State.Failure(failure, oldState.value)
                } else State.Failure(failure, oldState.value)
            } else State.Failure(UiText.Str("Default empty category is not set yet"), oldState.value)
        }
    }
}
