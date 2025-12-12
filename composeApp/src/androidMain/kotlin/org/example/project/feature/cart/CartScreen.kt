package org.example.project.feature.cart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.cart.CartComponent
import org.example.project.features.cart.CartViewEvent

@Composable
fun CartScreen(component: CartComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(CartViewEvent.OnBackClick)
    }

    CartContent(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        deliveryType = state.deliveryType,
        products = state.cartItems,
        productPrice = state.productsPrice,
        deliveryPrice = state.deliveryPrice,
        totalAmount = state.totalPrice,
        onBackButtonClicked = {
            component.onEvent(CartViewEvent.OnBackClick)
        },
        onAddToCart = {
            component.onEvent(CartViewEvent.OnAddToCart(it))
        },
        onRemoveFromCart = {
            component.onEvent(CartViewEvent.OnRemoveFromCart(it))
        },
        onConfirm = {
            component.onEvent(CartViewEvent.OnConfirmButtonClicked)
        }
    )
}