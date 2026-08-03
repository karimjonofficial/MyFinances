package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.getByCategory
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.UpdateProductTitleRequest
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.data.api.scoped.branch.add
import com.orka.myfinances.lib.data.api.scoped.branch.getChunk
import com.orka.myfinances.lib.data.api.scoped.branch.insert
import com.orka.myfinances.lib.data.api.scoped.branch.update
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.data.repositories.Chunk
import kotlinx.coroutines.flow.MutableSharedFlow

class ProductTitleRepository(
    private val api: ProductTitleApi,
    private val flow: MutableSharedFlow<ProductTitleEvent>
) : GetChunk<ProductTitleDto>, SearchChunk<ProductTitleDto>, GetById<ProductTitleDto>, Insert<AddProductTitleRequest>,
    Add<Id, AddProductTitleRequest>, UpdateProductTitle, GetProductTitlesByCategory {

    override suspend fun getChunk(
        size: Int,
        page: Int
    ): Chunk<ProductTitleDto>? {
        return searchChunk(size, page, null)
    }

    override suspend fun searchChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<ProductTitleDto>? {
        return api.getChunk(
            size = size,
            page = page,
            search = query
        )?.let { chunk ->
            Chunk(
                count = chunk.count,
                pageIndex = chunk.pageIndex,
                nextPageIndex = chunk.nextPageIndex,
                previousPageIndex = chunk.previousPageIndex,
                results = chunk.results.map { it.toDto() }
            )
        }
    }

    override suspend fun getById(id: Id): ProductTitleDto? {
        return api.getById(id)?.toDto()
    }

    override suspend fun insert(request: AddProductTitleRequest): Boolean {
        val success = api.insert(
            request = request,
            map = { officeId -> toApiRequest(officeId) }
        )
        if (success)
            flow.emit(ProductTitleEvent())
        return success
    }

    override suspend fun add(request: AddProductTitleRequest): Id? {
        val response = api.add(
            request = request,
            map = { officeId -> toApiRequest(officeId) }
        )
        if (response != null) {
            val id = Id(response.id)
            flow.emit(ProductTitleEvent(id))
            return id
        }
        else return null
    }

    override suspend fun update(id: Id, request: UpdateProductTitleRequest): Boolean {
        val success = api.update(
            id = id,
            request = request,
            map = { officeId -> toApiRequest(officeId) }
        )
        if (success) {
            flow.emit(ProductTitleEvent(id))
        }
        return success
    }

    override suspend fun getByCategory(
        size: Int,
        pageIndex: Int,
        categoryId: Id,
        search: String?
    ): Chunk<ProductTitleDto>? {
        return api.getByCategory(
            size = size,
            pageIndex = pageIndex,
            categoryId = categoryId,
            search = search
        )?.let { chunk ->
            Chunk(
                count = chunk.count,
                pageIndex = chunk.pageIndex,
                nextPageIndex = chunk.nextPageIndex,
                previousPageIndex = chunk.previousPageIndex,
                results = chunk.results.map { it.toDto() }
            )
        }
    }
}
