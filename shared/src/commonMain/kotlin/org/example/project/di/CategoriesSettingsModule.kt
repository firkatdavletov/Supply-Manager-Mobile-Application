package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.categories_settings.CategoriesSettingsCallbacks
import org.example.project.features.categories_settings.CategoriesSettingsComponent
import org.example.project.features.categories_settings.CategoriesSettingsStore
import org.example.project.features.categories_settings.DefaultCategoriesSettingsComponent
import org.koin.dsl.module

fun categoriesSettingsModule() = module {
    single { CategoriesSettingsStore() }

    single<CategoriesSettingsComponent> {
            (componentContext: ComponentContext, callbacks: CategoriesSettingsCallbacks) ->
        DefaultCategoriesSettingsComponent(
            componentContext = componentContext,
            snackBarManager = get(),
            callbacks = callbacks,
            categoriesStore = get(),
            getRemoteCategoriesUseCase = get(),
        )
    }
}
