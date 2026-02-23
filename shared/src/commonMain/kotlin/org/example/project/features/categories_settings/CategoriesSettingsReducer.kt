package org.example.project.features.categories_settings

import org.example.project.features.base.Reducer

class CategoriesSettingsReducer :
    Reducer<CategoriesSettingsViewState, CategoriesSettingsViewEvent, CategoriesSettingsViewEffect> {
    override fun reduce(
        state: CategoriesSettingsViewState,
        event: CategoriesSettingsViewEvent,
    ): CategoriesSettingsViewState {
        return when (event) {
            CategoriesSettingsViewEvent.OnBackClicked -> {
                state
            }

            CategoriesSettingsViewEvent.OnAddCategoryClicked -> {
                state
            }

            is CategoriesSettingsViewEvent.OnSearchQueryChanged -> {
                state.copy(searchQuery = event.value)
            }

            is CategoriesSettingsViewEvent.OnCategoryClicked -> {
                state.copy(selectedCategoryId = event.categoryId)
            }

            is CategoriesSettingsViewEvent.OnCategoriesLoaded -> {
                state.copy(
                    categories = event.categories,
                    isLoading = false,
                )
            }

            CategoriesSettingsViewEvent.OnLoading -> {
                state.copy(isLoading = true)
            }

            is CategoriesSettingsViewEvent.OnError -> {
                state.copy(isLoading = false)
            }

            is CategoriesSettingsViewEvent.OnThrowError -> {
                state.copy(isLoading = false)
            }
        }
    }

    override fun handleEvent(event: CategoriesSettingsViewEvent): CategoriesSettingsViewEffect? {
        return null
    }
}
