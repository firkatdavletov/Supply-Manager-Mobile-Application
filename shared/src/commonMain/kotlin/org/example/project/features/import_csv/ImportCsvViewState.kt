package org.example.project.features.import_csv

import org.example.project.domain.models.CatalogImportMode
import org.example.project.features.base.Reducer

data class ImportCsvViewState(
    val title: String = "Импорт CSV",
    val modes: List<CatalogImportMode> = CatalogImportMode.entries,
    val selectedMode: CatalogImportMode = CatalogImportMode.PRODUCTS,
    val selectedFileName: String? = null,
    val isLoading: Boolean = false,
    val responseTitle: String? = null,
    val responseDetails: String? = null,
    val isResponseError: Boolean = false,
) : Reducer.ViewState
