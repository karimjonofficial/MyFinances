package com.orka.myfinances.application.viewmodels.product.add

import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleRepository
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import com.orka.myfinances.ui.screens.product.add.interactor.CategoryItemModel
import kotlinx.coroutines.flow.asStateFlow

class AddProductTitleScreenViewModel(
    private val categoryId: Id,
    private val repository: FolderRepository,
    private val productTitleRepository: ProductTitleRepository,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<AddProductTitleScreenModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val categories = repository.getAll(null)
            ?.filterIsInstance<CategoryDto>()
            ?.map { it.toItemModel() }
        if (categories != null) {
            State.Success(AddProductTitleScreenModel(categories, categoryId))
        } else null
    },
    logger = logger
), AddProductTitleScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun addProductTitle(
        properties: List<PropertyModel<*>?>,
        name: String,
        price: Int?,
        salePrice: Int?,
        exposedPrice: Int?,
        description: String?,
        categoryId: Id
    ) {
        tryTransition { oldState ->
            if (oldState !is State.Success)
                return@tryTransition State.Failure(
                    UiText.Str("Action executed from wrong state"),
                    oldState.value
                )

            val category = oldState.value.categories.find { it.id == categoryId }
            val request = validate(
                category = category,
                name = name,
                price = price,
                salePrice = salePrice,
                exposedPrice = exposedPrice,
                description = description,
                properties = properties.filterNotNull()
            ) ?: return@tryTransition oldState

            val created = productTitleRepository.insert(request)
            if (created) {
                navigator.back()
                oldState
            } else State.Failure(failure, oldState.value)
        }
    }

    private fun validate(
        category: CategoryItemModel?,
        name: String,
        price: Int?,
        salePrice: Int?,
        exposedPrice: Int?,
        description: String?,
        properties: List<PropertyModel<*>>
    ): AddProductTitleRequest? {
        val isValid = (category != null) && name.isNotBlank() &&
                (price != null) && (salePrice != null) && (exposedPrice != null) &&
                (properties.size == category.template.fields.size) &&
                (price > 0) && (salePrice > 0) && (salePrice > price) &&
                (category.id.value > 0)

        return if (isValid) {
            AddProductTitleRequest(
                categoryId = category.id,
                name = name,
                price = price,
                salePrice = salePrice,
                exposedPrice = exposedPrice,
                properties = properties,
                description = description
            )
        } else null
    }
}