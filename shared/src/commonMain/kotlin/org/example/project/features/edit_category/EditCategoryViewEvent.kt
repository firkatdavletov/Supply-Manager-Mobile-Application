package org.example.project.features.edit_category

import org.example.project.features.base.Reducer

sealed interface EditCategoryViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : EditCategoryViewEvent

    data class OnNameChanged(val value: String) : EditCategoryViewEvent

    data class OnImageUrlChanged(val value: String) : EditCategoryViewEvent

    data object OnSaveClicked : EditCategoryViewEvent

    data object OnLoading : EditCategoryViewEvent

    data object OnSaved : EditCategoryViewEvent

    data class OnError(val error: String) : EditCategoryViewEvent

    data class OnThrowError(val throwable: Throwable) : EditCategoryViewEvent
}
