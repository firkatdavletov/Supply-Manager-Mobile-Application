package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.cart.CartComponent
import org.example.project.features.cart.CartViewCallbacks
import org.example.project.features.cart.DefaultCartComponent
import org.example.project.navigation.DefaultRootComponent
import org.koin.core.module.Module
import org.koin.dsl.module

fun cartModule(): Module = module {
    single <CartComponent> { (componentContext: ComponentContext, config: DefaultRootComponent.Config.Cart ,callbacks: CartViewCallbacks) ->
        DefaultCartComponent(
            componentContext = componentContext,
            callbacks = callbacks,
            loadCartUseCase = get(),
            addToCartUseCase = get(),
            removeFromCartUseCase = get(),
            getProductUseCase = get(),
            cartRepository = get(),
            securityStorage = get(),
            snackBarManager = get()
        )
    }
}