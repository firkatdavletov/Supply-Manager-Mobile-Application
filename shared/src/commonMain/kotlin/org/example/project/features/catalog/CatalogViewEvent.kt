package org.example.project.features.catalog

import org.example.project.domain.models.CartModel
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.features.base.Reducer

sealed interface CatalogViewEvent : Reducer.ViewEvent {
    data class OnCartLoaded(val cartModel: CartModel) : CatalogViewEvent

    data class OnCategoryClicked(val categoryId: Long) : CatalogViewEvent

    data class OnCategoriesLoaded(val categories: List<CategoryModel>) : CatalogViewEvent

    data class OnCategoryLoaded(val categoryModel: CategoryModel) : CatalogViewEvent

    data class OnAddToCart(val product: ProductModel) : CatalogViewEvent

    data class OnRemoveFromCart(val product: ProductModel) : CatalogViewEvent

    data class OnProductClicked(val id: Long) : CatalogViewEvent

    data object OnBackClicked : CatalogViewEvent

    data object OnCartButtonClicked : CatalogViewEvent
}