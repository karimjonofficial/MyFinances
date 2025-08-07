package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.sources.ProductTemplateDataSource

interface ProductTemplateDataSourceFactory {
    fun make(): ProductTemplateDataSource
}