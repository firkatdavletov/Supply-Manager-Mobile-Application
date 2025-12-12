package org.example.project.features.home

import org.example.project.domain.models.CartItemModel
import org.example.project.domain.models.CartModel
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.OrderModel
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.UserModel
import org.example.project.features.base.Reducer

sealed interface HomeViewEvent: Reducer.ViewEvent {
    data object OnAddressClicked: HomeViewEvent
    data object OnCartButtonClicked: HomeViewEvent
    data object OnProfileClicked: HomeViewEvent
    data class OnCategoriesLoaded(val categories: List<CategoryModel>, val cartItems: List<CartItemModel>): HomeViewEvent
    data class OnCategoryClicked(val categoryId: Long, val categoryTitle: String): HomeViewEvent
    data class OnCartLoaded(val cartModel: CartModel): HomeViewEvent
    data class OnError(val text: String?): HomeViewEvent
    data class OnThrowError(val throwable: Throwable) : HomeViewEvent
    data class OnAddToCart(val product: ProductModel): HomeViewEvent
    data class OnRemoveFromCart(val product: ProductModel): HomeViewEvent
    data class OnCurrentOrderLoaded(val orders: List<OrderModel>): HomeViewEvent
    data class OnOrderClicked(val id: Long) : HomeViewEvent
    data class OnUserLoaded(val userModel: UserModel?) : HomeViewEvent
}