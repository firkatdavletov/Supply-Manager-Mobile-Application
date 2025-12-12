package org.example.project.features.catalog

import org.example.project.domain.models.CartModel
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.UserModel
import org.example.project.features.base.Reducer

sealed interface CatalogViewEvent: Reducer.ViewEvent {
    data object OnAddressClicked: CatalogViewEvent
    data class OnUserLoaded(val user: UserModel): CatalogViewEvent
    data class OnProductsLoaded(val products: List<ProductModel>): CatalogViewEvent
    data class OnCartLoaded(val cartModel: CartModel): CatalogViewEvent
    data class OnCategoryClicked(val categoryId: Long): CatalogViewEvent
    data class OnCategoryLoaded(val category: CategoryModel): CatalogViewEvent
    data object OnBackClicked: CatalogViewEvent
    data class OnAddToCart(val product: ProductModel): CatalogViewEvent
    data class OnRemoveFromCart(val product: ProductModel): CatalogViewEvent
    data object OnCartButtonClicked: CatalogViewEvent
}