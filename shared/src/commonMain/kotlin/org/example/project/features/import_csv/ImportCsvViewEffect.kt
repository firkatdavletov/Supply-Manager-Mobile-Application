package org.example.project.features.import_csv

import org.example.project.features.base.Reducer

sealed interface ImportCsvViewEffect : Reducer.ViewEffect {
    data object None : ImportCsvViewEffect
}
