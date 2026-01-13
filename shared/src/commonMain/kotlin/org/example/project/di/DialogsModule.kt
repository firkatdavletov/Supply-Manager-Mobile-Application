package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.dialogs.product_card.DefaultProductCardComponent
import org.example.project.dialogs.product_card.ProductCardComponent
import org.example.project.navigation.DialogConfig
import org.koin.dsl.module

fun dialogsModule() = module {
    factory <ProductCardComponent>{ (componentContext: ComponentContext, config: DialogConfig.ProductCard) ->
        DefaultProductCardComponent(
            componentContent = componentContext,
            productId = config.productId,
            snackBarManager = get(),
            getProductCardUseCase = get(),
            addToCartUseCase = get(),
            removeFromCartUseCase = get(),
            cartRepository = get()
        )
    }
}