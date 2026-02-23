package org.example.project.features.categories_settings

import org.example.project.domain.models.CategoryModel
import org.example.project.features.base.Reducer

sealed interface CategoriesSettingsViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : CategoriesSettingsViewEvent

    data object OnAddCategoryClicked : CategoriesSettingsViewEvent

    data class OnSearchQueryChanged(val value: String) : CategoriesSettingsViewEvent

    data class OnCategoryClicked(val categoryId: Long) : CategoriesSettingsViewEvent

    data class OnCategoriesLoaded(val categories: List<CategoryModel>) : CategoriesSettingsViewEvent

    data object OnLoading : CategoriesSettingsViewEvent

    data class OnError(val error: String) : CategoriesSettingsViewEvent

    data class OnThrowError(val throwable: Throwable) : CategoriesSettingsViewEvent
}
