package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.import_csv.DefaultImportCsvComponent
import org.example.project.features.import_csv.ImportCsvCallbacks
import org.example.project.features.import_csv.ImportCsvComponent
import org.koin.dsl.module

fun importCsvModule() = module {
    single<ImportCsvComponent> { (componentContext: ComponentContext, callbacks: ImportCsvCallbacks) ->
        DefaultImportCsvComponent(
            componentContext = componentContext,
            snackBarManager = get(),
            callbacks = callbacks,
            importCatalogCsvUseCase = get(),
        )
    }
}
