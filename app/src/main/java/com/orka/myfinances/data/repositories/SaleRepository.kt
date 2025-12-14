package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.fixtures.resources.models.sale.sales
import com.orka.myfinances.lib.data.Repository
import kotlinx.coroutines.delay

class SaleRepository : Repository<Sale> {

    override suspend fun get(): List<Sale> {
        delay(1000)
        return sales
    }
}