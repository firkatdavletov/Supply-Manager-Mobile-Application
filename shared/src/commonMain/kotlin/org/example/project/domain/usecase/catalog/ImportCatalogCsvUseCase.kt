package org.example.project.domain.usecase.catalog

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.CatalogImportMode
import org.example.project.domain.models.CatalogImportResultModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CatalogImportRepository
import org.example.project.domain.usecase.base.IOUseCase

class ImportCatalogCsvUseCase(
    private val repository: CatalogImportRepository,
) : IOUseCase<ImportCatalogCsvUseCase.Params, ResultModel<CatalogImportResultModel>>() {
    override fun execute(param: Params): Flow<ResultModel<CatalogImportResultModel>> {
        return repository.importCsv(
            mode = param.mode,
            fileName = param.fileName,
            fileBytes = param.fileBytes,
        )
    }

    data class Params(
        val mode: CatalogImportMode,
        val fileName: String,
        val fileBytes: ByteArray,
    )
}
