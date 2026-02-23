package org.example.project.features.import_csv

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.CatalogImportResultModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.usecase.catalog.ImportCatalogCsvUseCase
import org.example.project.features.SnackBarManager

class DefaultImportCsvComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: ImportCsvCallbacks,
    private val importCatalogCsvUseCase: ImportCatalogCsvUseCase,
) : ImportCsvComponent(
        componentContext = componentContext,
        initialState = ImportCsvViewState(),
        reducer = ImportCsvReducer(),
        snackBarManager = snackBarManager,
    ) {
    private var selectedFileBytes: ByteArray? = null

    override fun onEvent(event: ImportCsvViewEvent) {
        when (event) {
            ImportCsvViewEvent.OnBackClicked -> {
                callbacks.navigateBack()
            }

            is ImportCsvViewEvent.OnModeSelected -> {
                reduce(event)
            }

            is ImportCsvViewEvent.OnFileSelected -> {
                selectedFileBytes = event.fileBytes
                reduce(event)
            }

            ImportCsvViewEvent.OnImportClicked -> {
                importCsv()
            }

            ImportCsvViewEvent.OnLoading -> {
                reduce(event)
            }

            is ImportCsvViewEvent.OnImportSuccess -> {
                selectedFileBytes = null
                reduce(event)
            }

            is ImportCsvViewEvent.OnError -> {
                reduce(event)
                showError(event.error)
            }

            is ImportCsvViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
        }
    }

    private fun importCsv() {
        val fileBytes = selectedFileBytes

        if (fileBytes == null || state.value.selectedFileName.isNullOrBlank()) {
            onEvent(ImportCsvViewEvent.OnError("Сначала выберите CSV файл"))
            return
        }

        val params = ImportCatalogCsvUseCase.Params(
            mode = state.value.selectedMode,
            fileName = state.value.selectedFileName.orEmpty(),
            fileBytes = fileBytes,
        )

        coroutineScope.launch {
            importCatalogCsvUseCase
                .invoke(params)
                .catch {
                    withContext(Dispatchers.Main) {
                        onEvent(ImportCsvViewEvent.OnThrowError(it))
                    }
                }.collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            withContext(Dispatchers.Main) {
                                onEvent(ImportCsvViewEvent.OnError(resultModel.message ?: "Что-то пошло не так"))
                            }
                        }

                        ResultModel.Loading -> {
                            withContext(Dispatchers.Main) {
                                onEvent(ImportCsvViewEvent.OnLoading)
                            }
                        }

                        is ResultModel.Success<CatalogImportResultModel> -> {
                            withContext(Dispatchers.Main) {
                                onEvent(ImportCsvViewEvent.OnImportSuccess(resultModel.data))
                            }
                        }
                    }
                }
        }
    }
}
