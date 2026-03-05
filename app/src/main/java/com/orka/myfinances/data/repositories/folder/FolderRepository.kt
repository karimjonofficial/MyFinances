package com.orka.myfinances.data.repositories.folder

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetByParameter
import kotlinx.coroutines.flow.Flow

interface FolderRepository: Get<Folder>, Add<Folder, AddFolderRequest>,
    GetByParameter<Folder, Catalog>, GetTopFolders {
        val events: Flow<FolderEvent>
    }