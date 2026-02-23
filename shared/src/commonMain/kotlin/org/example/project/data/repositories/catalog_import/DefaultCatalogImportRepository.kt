package org.example.project.data.repositories.catalog_import

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.data.datastore.remote.catalog_import.CatalogImportRemoteDataStore
import org.example.project.domain.models.CatalogImportMode
import org.example.project.domain.models.CatalogImportResultModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CatalogImportRepository

class DefaultCatalogImportRepository(
    private val remoteDataStore: CatalogImportRemoteDataStore,
) : CatalogImportRepository {
    override fun importCsv(
        mode: CatalogImportMode,
        fileName: String,
        fileBytes: ByteArray,
    ): Flow<ResultModel<CatalogImportResultModel>> {
        return flow {
            emit(ResultModel.Loading)

            val response = remoteDataStore.importCsv(mode, fileName, fileBytes)

            if (response.success) {
                emit(
                    ResultModel.Success(
                        CatalogImportResultModel(
                            message = response.message,
                            details = response.details,
                        ),
                    ),
                )
            } else {
                val errorMessage =
                    listOfNotNull(
                        response.error,
                        response.message,
                        response.details,
                    ).distinct().joinToString(separator = "\n").ifBlank {
                        null
                    }
                emit(ResultModel.Error(errorMessage, response.code))
            }
        }
    }
}
