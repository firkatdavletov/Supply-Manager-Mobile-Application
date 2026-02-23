package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.catalog_settings.CatalogSettingsCallbacks
import org.example.project.features.catalog_settings.CatalogSettingsComponent
import org.example.project.features.catalog_settings.DefaultCatalogSettingsComponent
import org.koin.dsl.module

fun catalogSettingsModule() = module {
    single<CatalogSettingsComponent> { (componentContext: ComponentContext, callbacks: CatalogSettingsCallbacks) ->
        DefaultCatalogSettingsComponent(
            componentContext = componentContext,
            callbacks = callbacks,
        )
    }
}
