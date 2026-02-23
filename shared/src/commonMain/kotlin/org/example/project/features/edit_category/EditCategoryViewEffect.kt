package org.example.project.features.edit_category

import org.example.project.features.base.Reducer

sealed interface EditCategoryViewEffect : Reducer.ViewEffect {
    data object None : EditCategoryViewEffect
}
