package org.example.project.data.api.catalog_import

import org.example.project.data.api.catalog_import.model.ImportCatalogCsvResponseBody
import org.example.project.domain.models.CatalogImportMode

interface CatalogImportApi {
    suspend fun importCsv(
        mode: CatalogImportMode,
        fileName: String,
        fileBytes: ByteArray,
    ): ImportCatalogCsvResponseBody
}
