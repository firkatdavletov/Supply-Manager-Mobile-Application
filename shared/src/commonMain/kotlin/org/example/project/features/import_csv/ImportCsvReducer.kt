package org.example.project.features.import_csv

import org.example.project.features.base.Reducer

class ImportCsvReducer : Reducer<ImportCsvViewState, ImportCsvViewEvent, ImportCsvViewEffect> {
    override fun reduce(
        state: ImportCsvViewState,
        event: ImportCsvViewEvent,
    ): ImportCsvViewState {
        return when (event) {
            ImportCsvViewEvent.OnBackClicked -> {
                state
            }

            is ImportCsvViewEvent.OnModeSelected -> {
                state.copy(selectedMode = event.mode)
            }

            is ImportCsvViewEvent.OnFileSelected -> {
                state.copy(selectedFileName = event.fileName)
            }

            ImportCsvViewEvent.OnImportClicked -> {
                state
            }

            ImportCsvViewEvent.OnLoading -> {
                state.copy(
                    isLoading = true,
                    responseTitle = null,
                    responseDetails = null,
                    isResponseError = false,
                )
            }

            is ImportCsvViewEvent.OnImportSuccess -> {
                state.copy(
                    isLoading = false,
                    selectedFileName = null,
                    responseTitle = event.result.message ?: "Импорт выполнен",
                    responseDetails = event.result.details,
                    isResponseError = false,
                )
            }

            is ImportCsvViewEvent.OnError -> {
                state.copy(
                    isLoading = false,
                    responseTitle = "Ошибка импорта",
                    responseDetails = event.error,
                    isResponseError = true,
                )
            }

            is ImportCsvViewEvent.OnThrowError -> {
                state.copy(
                    isLoading = false,
                    responseTitle = "Ошибка импорта",
                    responseDetails = event.throwable.message ?: "Что-то пошло не так",
                    isResponseError = true,
                )
            }
        }
    }

    override fun handleEvent(event: ImportCsvViewEvent): ImportCsvViewEffect? {
        return null
    }
}
