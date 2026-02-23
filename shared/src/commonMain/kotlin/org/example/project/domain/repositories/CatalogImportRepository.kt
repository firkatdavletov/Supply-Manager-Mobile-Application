package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.CatalogImportMode
import org.example.project.domain.models.CatalogImportResultModel
import org.example.project.domain.models.ResultModel

interface CatalogImportRepository {
    fun importCsv(
        mode: CatalogImportMode,
        fileName: String,
        fileBytes: ByteArray,
    ): Flow<ResultModel<CatalogImportResultModel>>
}
