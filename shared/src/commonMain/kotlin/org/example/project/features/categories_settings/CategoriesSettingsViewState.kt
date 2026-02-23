package org.example.project.features.categories_settings

import org.example.project.domain.models.CategoryModel
import org.example.project.features.base.Reducer

data class CategoriesSettingsViewState(
    val title: String = "Карточки категорий",
    val categories: List<CategoryModel> = emptyList(),
    val searchQuery: String = "",
    val selectedCategoryId: Long? = null,
    val isLoading: Boolean = false,
) : Reducer.ViewState
