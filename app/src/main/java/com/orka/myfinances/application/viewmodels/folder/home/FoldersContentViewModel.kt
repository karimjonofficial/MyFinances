package com.orka.myfinances.application.viewmodels.folder.home

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.data.repositories.PinnedCategoriesEvent
import com.orka.myfinances.application.viewmodels.folder.toUiModel
import com.orka.myfinances.data.models.Id
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
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.refreshable.RefreshableBaseViewModel
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
    pinnedCategoriesFlow: Flow<PinnedCategoriesEvent>,
    logger: Logger
) : RefreshableBaseViewModel<FoldersContentModel>(
    produceInitialState = {
        val folders = getTop.getTop()
        val categories = pinnedCategoriesRepository.getAll()

        if (folders != null) {
            val isDefaultSet = getDefaultCategory.getDefaultCategoryId() != null
            val model = FoldersContentModel(
                folders = folders.map { it.toUiModel() },
                isDefaultCategorySet = isDefaultSet,
                pinnedCategories = categories
            )
            State.Success(model)
        } else State.Failure()
    },
    logger = logger
), FoldersContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        folderFlow.onEach {
            if (it.catalogId == null) initialize()
        }.launchIn(viewModelScope)

        pinnedCategoriesFlow.onEach { initialize() }.launchIn(viewModelScope)
    }

    override fun addFolder(name: String, type: String, templateId: Id?) {
        tryTransition { oldState ->
            val request = AddFolderRequest(name, type, templateId, null)
            val response = addFolder.add(request)
            if(response != null) oldState
            else State.Failure(FolderNotAdded, oldState.value)
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
                    val item = AddReceiveRequestItem(
                        productTitleId = r1,
                        price = price,
                        salePrice = salePrice,
                        exposedPrice = exposedPrice,
                        amount = amount
                    )
                    val receiveRequest = AddReceiveRequest(
                        categoryId = id,
                        items = listOf(item),
                        price = price * amount
                    )
                    val r2 = addReceive.insert(receiveRequest)
                    if(r2) oldState
                    else State.Failure(ReceiveNotAdded, oldState.value)
                } else State.Failure(ProductTitleNotAdded, oldState.value)
            } else State.Failure(DefaultCategoryNotFound, oldState.value)
        }
    }
}