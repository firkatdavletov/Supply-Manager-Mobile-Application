package org.example.project.features.import_csv

import org.example.project.domain.models.CatalogImportMode
import org.example.project.domain.models.CatalogImportResultModel
import org.example.project.features.base.Reducer

sealed interface ImportCsvViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : ImportCsvViewEvent

    data class OnModeSelected(val mode: CatalogImportMode) : ImportCsvViewEvent

    data class OnFileSelected(
        val fileName: String,
        val fileBytes: ByteArray,
    ) : ImportCsvViewEvent

    data object OnImportClicked : ImportCsvViewEvent

    data object OnLoading : ImportCsvViewEvent

    data class OnImportSuccess(val result: CatalogImportResultModel) : ImportCsvViewEvent

    data class OnError(val error: String) : ImportCsvViewEvent

    data class OnThrowError(val throwable: Throwable) : ImportCsvViewEvent
}
