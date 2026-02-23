package org.example.project.features.products_settings

import org.example.project.domain.models.ProductModel
import org.example.project.features.base.Reducer

sealed interface ProductsSettingsViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : ProductsSettingsViewEvent

    data object OnAddProductClicked : ProductsSettingsViewEvent

    data class OnSearchQueryChanged(val value: String) : ProductsSettingsViewEvent

    data class OnProductClicked(val productId: Long) : ProductsSettingsViewEvent

    data class OnProductsLoaded(val products: List<ProductModel>) : ProductsSettingsViewEvent

    data object OnLoading : ProductsSettingsViewEvent

    data class OnError(val error: String) : ProductsSettingsViewEvent

    data class OnThrowError(val throwable: Throwable) : ProductsSettingsViewEvent
}
