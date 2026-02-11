package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.Generator

class IdGenerator : Generator<Id> {
    private var count = 3

    override fun generate(): Id {
        return Id(count++)
    }
}