package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.products_settings.DefaultProductsSettingsComponent
import org.example.project.features.products_settings.ProductsSettingsCallbacks
import org.example.project.features.products_settings.ProductsSettingsComponent
import org.example.project.features.products_settings.ProductsSettingsStore
import org.koin.dsl.module

fun productsSettingsModule() = module {
    single { ProductsSettingsStore() }

    single<ProductsSettingsComponent> {
            (componentContext: ComponentContext, callbacks: ProductsSettingsCallbacks) ->
        DefaultProductsSettingsComponent(
            componentContext = componentContext,
            snackBarManager = get(),
            callbacks = callbacks,
            productsStore = get(),
            getAllProductsUseCase = get(),
        )
    }
}
