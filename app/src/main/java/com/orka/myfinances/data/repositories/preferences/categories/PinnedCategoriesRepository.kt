package com.orka.myfinances.data.repositories.preferences.categories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get

interface PinnedCategoriesRepository : Get<Id>, Add<Unit, AddPinnedCategoryRequest>