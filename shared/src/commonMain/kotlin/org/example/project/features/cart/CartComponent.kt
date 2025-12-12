package org.example.project.features.cart

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class CartComponent(
    componentContext: ComponentContext,
    initialState: CartViewState,
    snackBarManager: SnackBarManager,
    reducer: CartReducer,
): BaseComponent<CartViewState, CartViewEvent, CartViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = reducer,
    snackBarManager = snackBarManager,
)
