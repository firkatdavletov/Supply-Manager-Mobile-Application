package org.example.project.features.catalog

import org.example.project.features.base.Reducer

interface CatalogViewEffect: Reducer.ViewEffect {
    data object None: CatalogViewEffect
}