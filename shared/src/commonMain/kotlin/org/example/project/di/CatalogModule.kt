package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.catalog.CatalogCallbacks
import org.example.project.features.catalog.CatalogComponent
import org.example.project.features.catalog.DefaultCatalogComponent
import org.koin.dsl.module

fun catalogModule() = module {
    factory<CatalogComponent> { (componentContext: ComponentContext, categoryId: Int?, callbacks: CatalogCallbacks) ->
        DefaultCatalogComponent(
            componentContext = componentContext,
            categoryId = categoryId,
            callbacks = callbacks,
            getCategoriesUseCase = get(),
            getCategoryUseCase = get(),
            addToCartUseCase = get(),
            removeFromCartUseCase = get(),
            cartRepository = get()
        )
    }
}