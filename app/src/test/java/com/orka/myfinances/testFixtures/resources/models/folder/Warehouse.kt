package com.orka.myfinances.testFixtures.resources.models.folder

import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.models.id2
import com.orka.myfinances.testFixtures.resources.models.template.template
import com.orka.myfinances.testFixtures.resources.name

val warehouse1 = Warehouse(
    id = id1,
    name = name,
    template = template
)
val warehouse2 = Warehouse(
    id = id2,
    name = name,
    template = template
)