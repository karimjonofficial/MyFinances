package com.orka.myfinances.ui.screens.catalog.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderModel
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.api.template.TemplateApiModel
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.home.viewmodel.folder.TemplateState
import com.orka.myfinances.ui.screens.home.viewmodel.folder.toUiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatalogScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
    private val office: Office,
    events: Flow<FolderEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    private val _dialogState = MutableStateFlow<TemplateState>(TemplateState.Initial)
    val dialogState = _dialogState.asStateFlow()
    val uiState = state.asStateFlow()

    init {
        events.onEach {
            if(it.catalogId == id) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            try {
                val catalogResponse = client.get("categories/${id.value}/")
                if (catalogResponse.status == HttpStatusCode.OK) {
                    val catalog = catalogResponse.body<FolderModel>().map() as Catalog
                    
                    val folders = client.get(
                        urlString = "categories/",
                        block = {
                            parameter("parent_id", id.value)
                            parameter("branch", office.id.value)
                        }
                    ).body<List<FolderModel>>().map { it.map() }

                    setState(State.Success(CatalogScreenStateSuccess(catalog, folders.map { it.toUiModel() })))
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    fun initDialog() {
        launch {
            try {
                val response = client.get("templates/")
                if (response.status == HttpStatusCode.OK) {
                    val templates = response.body<List<TemplateApiModel>>().map { it.map() }
                    _dialogState.value = TemplateState.Success(templates)
                } else {
                    _dialogState.value = TemplateState.Error
                }
            } catch (_: Exception) {
                _dialogState.value = TemplateState.Error
            }
        }
    }

    fun addFolder(
        name: String,
        type: String,
        templateId: Id?
    ) {
        launch {
            val request = AddFolderRequest(
                name = name,
                type = type,
                templateId = templateId,
                parentId = id
            )
            client.post(
                urlString = "categories/",
                block = { setBody(request.map()) }
            )
        }
    }

    fun select(folder: Folder) {
        launch {
            when (folder) {
                is Catalog -> navigator.navigateToCatalog(folder.id)
                is Category -> navigator.navigateToCategory(folder.id)
            }
        }
    }

    fun navigateToAddTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }
}

data class CatalogScreenStateSuccess(
    val catalog: Catalog,
    val folders: List<com.orka.myfinances.ui.screens.home.models.FolderUiModel>
)
