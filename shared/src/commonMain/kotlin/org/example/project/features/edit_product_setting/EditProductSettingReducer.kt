package org.example.project.features.edit_product_setting

import org.example.project.features.base.Reducer

class EditProductSettingReducer :
    Reducer<EditProductSettingViewState, EditProductSettingViewEvent, EditProductSettingViewEffect> {
    override fun reduce(
        state: EditProductSettingViewState,
        event: EditProductSettingViewEvent,
    ): EditProductSettingViewState {
        return when (event) {
            EditProductSettingViewEvent.OnBackClicked -> {
                state
            }

            is EditProductSettingViewEvent.OnNameChanged -> {
                state.copy(name = event.value)
            }

            is EditProductSettingViewEvent.OnDescriptionChanged -> {
                state.copy(description = event.value)
            }

            is EditProductSettingViewEvent.OnPriceChanged -> {
                state.copy(price = event.value)
            }

            is EditProductSettingViewEvent.OnImageUrlChanged -> {
                state.copy(imageUrl = event.value)
            }

            EditProductSettingViewEvent.OnSaveClicked -> {
                state
            }

            EditProductSettingViewEvent.OnLoading -> {
                state.copy(isLoading = true)
            }

            EditProductSettingViewEvent.OnSaved -> {
                state.copy(isLoading = false)
            }

            is EditProductSettingViewEvent.OnError -> {
                state.copy(isLoading = false)
            }

            is EditProductSettingViewEvent.OnThrowError -> {
                state.copy(isLoading = false)
            }
        }
    }

    override fun handleEvent(event: EditProductSettingViewEvent): EditProductSettingViewEffect? {
        return null
    }
}
