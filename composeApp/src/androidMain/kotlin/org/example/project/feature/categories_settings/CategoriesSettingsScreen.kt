package org.example.project.feature.categories_settings

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.categories_settings.CategoriesSettingsComponent
import org.example.project.features.categories_settings.CategoriesSettingsViewEvent

@Composable
fun CategoriesSettingsScreen(component: CategoriesSettingsComponent) {
    val state by component.state.subscribeAsState()
    val normalizedQuery = state.searchQuery.trim()
    val filteredCategories =
        if (normalizedQuery.isEmpty()) {
            state.categories
        } else {
            state.categories.filter { category ->
                category.title.contains(normalizedQuery, ignoreCase = true)
            }
        }

    BackHandler {
        component.onEvent(CategoriesSettingsViewEvent.OnBackClicked)
    }

    CategoriesSettingsContent(
        title = state.title,
        categories = filteredCategories,
        searchQuery = state.searchQuery,
        selectedCategoryId = state.selectedCategoryId,
        isLoading = state.isLoading,
        onBackClicked = {
            component.onEvent(CategoriesSettingsViewEvent.OnBackClicked)
        },
        onAddCategoryClicked = {
            component.onEvent(CategoriesSettingsViewEvent.OnAddCategoryClicked)
        },
        onSearchQueryChanged = { value ->
            component.onEvent(CategoriesSettingsViewEvent.OnSearchQueryChanged(value))
        },
        onCategoryClicked = { categoryId ->
            component.onEvent(CategoriesSettingsViewEvent.OnCategoryClicked(categoryId))
        },
    )
}
