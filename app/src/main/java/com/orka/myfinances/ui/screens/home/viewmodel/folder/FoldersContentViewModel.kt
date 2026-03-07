package com.orka.myfinances.ui.screens.home.viewmodel.folder

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderModel
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.api.template.TemplateApiModel
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.lib.viewmodel.DualStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.asStateFlow

class FoldersContentViewModel(
    private val client: HttpClient,
    private val office: Office,
    private val navigator: Navigator,
    logger: Logger
) : DualStateViewModel<FoldersState, TemplateState>(
    initialState1 = FoldersState.Initial,
    initialState2 = TemplateState.Initial,
    logger = logger
), FoldersInteractor {
    val uiState = state1.asStateFlow()
    val dialogState = state2.asStateFlow()

    override fun initialize() {
        launch {
            setState1(FoldersState.Loading)
            try {
                val folders = client.get(
                    urlString = "categories/",
                    block = { parameter(key = "branch", value = office.id.value) }
                ).body<List<FolderModel>>().map { it.map() }

                setState1(FoldersState.Success(folders.map { it.toUiModel() }))
            } catch (_: Exception) {
                setState1(FoldersState.Error)
            }
            try {
                val templates = client.get("templates/").body<List<TemplateApiModel>>().map { it.map() }
                setState2(TemplateState.Success(templates))
            } catch (_: Exception) {
                setState2(TemplateState.Error)
            }
        }
    }

    override fun addFolder(name: String, type: String, templateId: Id?) {
        launch {
            setState1(FoldersState.Loading)
            val request = AddFolderRequest(name, type, templateId, null)
            client.post(
                urlString = "categories/",
                block = { setBody(request.map()) }
            ).body<FolderModel>().map()

            val folders = client.get(
                urlString = "categories/",
                block = { parameter(key = "branch", value = office.id.value) }
            ).body<List<FolderModel>>().map { it.map() }

            setState1(FoldersState.Success(folders.map { it.toUiModel() }))
        }
    }

    override fun select(folder: Folder) {
        launch {
            when (folder) {
                is Catalog -> navigator.navigateToCatalog(folder)
                is Category -> navigator.navigateToCategory(folder)
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
}
