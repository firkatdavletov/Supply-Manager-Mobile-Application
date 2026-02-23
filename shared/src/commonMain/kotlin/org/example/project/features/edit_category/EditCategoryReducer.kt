package org.example.project.features.edit_category

import org.example.project.features.base.Reducer

class EditCategoryReducer : Reducer<EditCategoryViewState, EditCategoryViewEvent, EditCategoryViewEffect> {
    override fun reduce(
        state: EditCategoryViewState,
        event: EditCategoryViewEvent,
    ): EditCategoryViewState {
        return when (event) {
            EditCategoryViewEvent.OnBackClicked -> {
                state
            }

            is EditCategoryViewEvent.OnNameChanged -> {
                state.copy(name = event.value)
            }

            is EditCategoryViewEvent.OnImageUrlChanged -> {
                state.copy(imageUrl = event.value)
            }

            EditCategoryViewEvent.OnSaveClicked -> {
                state
            }

            EditCategoryViewEvent.OnLoading -> {
                state.copy(isLoading = true)
            }

            EditCategoryViewEvent.OnSaved -> {
                state.copy(isLoading = false)
            }

            is EditCategoryViewEvent.OnError -> {
                state.copy(isLoading = false)
            }

            is EditCategoryViewEvent.OnThrowError -> {
                state.copy(isLoading = false)
            }
        }
    }

    override fun handleEvent(event: EditCategoryViewEvent): EditCategoryViewEffect? {
        return null
    }
}
