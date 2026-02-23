package org.example.project.features.edit_product_setting

import org.example.project.features.base.Reducer

sealed interface EditProductSettingViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : EditProductSettingViewEvent

    data class OnNameChanged(val value: String) : EditProductSettingViewEvent

    data class OnDescriptionChanged(val value: String) : EditProductSettingViewEvent

    data class OnPriceChanged(val value: String) : EditProductSettingViewEvent

    data class OnImageUrlChanged(val value: String) : EditProductSettingViewEvent

    data object OnSaveClicked : EditProductSettingViewEvent

    data object OnLoading : EditProductSettingViewEvent

    data object OnSaved : EditProductSettingViewEvent

    data class OnError(val error: String) : EditProductSettingViewEvent

    data class OnThrowError(val throwable: Throwable) : EditProductSettingViewEvent
}
