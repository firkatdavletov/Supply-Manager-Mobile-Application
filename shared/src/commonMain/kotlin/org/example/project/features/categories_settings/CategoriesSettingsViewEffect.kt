package org.example.project.features.categories_settings

import org.example.project.features.base.Reducer

sealed interface CategoriesSettingsViewEffect : Reducer.ViewEffect {
    data object None : CategoriesSettingsViewEffect
}
