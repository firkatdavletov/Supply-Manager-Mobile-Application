package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.catalog.CatalogCallbacks
import org.example.project.features.catalog.CatalogComponent
import org.example.project.features.catalog.DefaultCatalogComponent
import org.koin.dsl.module

fun catalogModule() = module {
    factory<CatalogComponent> { (componentContext: ComponentContext, categoryId: Long, title: String, callbacks: CatalogCallbacks) ->
        DefaultCatalogComponent(
            componentContext = componentContext,
            categoryId = categoryId,
            title = title,
            callbacks = callbacks,
            getProductsUseCase = get(),
            addToCartUseCase = get(),
            removeFromCartUseCase = get(),
            cartRepository = get()
        )
    }
}