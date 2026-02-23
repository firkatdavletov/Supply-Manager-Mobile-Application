package org.example.project.features.products_settings

import org.example.project.domain.models.ProductModel
import org.example.project.features.base.Reducer

data class ProductsSettingsViewState(
    val title: String = "Карточки товаров",
    val products: List<ProductModel> = emptyList(),
    val searchQuery: String = "",
    val selectedProductId: Long? = null,
    val isLoading: Boolean = false,
) : Reducer.ViewState
