package org.example.project.data.datastore.remote.catalog_import

import org.example.project.data.api.catalog_import.CatalogImportApi
import org.example.project.data.api.catalog_import.model.ImportCatalogCsvResponseBody
import org.example.project.domain.models.CatalogImportMode

class DefaultCatalogImportRemoteDataStore(
    private val api: CatalogImportApi,
) : CatalogImportRemoteDataStore {
    override suspend fun importCsv(
        mode: CatalogImportMode,
        fileName: String,
        fileBytes: ByteArray,
    ): ImportCatalogCsvResponseBody {
        return api.importCsv(mode, fileName, fileBytes)
    }
}
