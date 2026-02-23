package org.example.project.features.products_settings

import org.example.project.features.base.Reducer

sealed interface ProductsSettingsViewEffect : Reducer.ViewEffect {
    data object None : ProductsSettingsViewEffect
}
