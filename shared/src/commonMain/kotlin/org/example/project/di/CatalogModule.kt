package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.catalog.CatalogCallbacks
import org.example.project.features.catalog.CatalogComponent
import org.example.project.features.catalog.DefaultCatalogComponent
import org.koin.dsl.module

fun catalogModule() =
    module {
        factory<CatalogComponent> { parameters ->
            DefaultCatalogComponent(
                componentContext = parameters.component1() as ComponentContext,
                categoryId = parameters.component2() as Int?,
                callbacks = parameters.component3() as CatalogCallbacks,
                getCategoriesUseCase = get(),
                getCategoryUseCase = get(),
                addToCartUseCase = get(),
                removeFromCartUseCase = get(),
                cartRepository = get(),
                loadCartUseCase = get(),
            )
        }
    }