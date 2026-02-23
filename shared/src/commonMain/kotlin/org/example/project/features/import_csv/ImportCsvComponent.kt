package org.example.project.features.import_csv

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class ImportCsvComponent(
    componentContext: ComponentContext,
    initialState: ImportCsvViewState,
    reducer: ImportCsvReducer,
    snackBarManager: SnackBarManager? = null,
) : BaseComponent<ImportCsvViewState, ImportCsvViewEvent, ImportCsvViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager,
)
