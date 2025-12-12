package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.launch.DefaultLaunchComponent
import org.example.project.features.launch.LaunchComponent
import org.example.project.features.launch.LaunchNavigationCallbacks
import org.koin.dsl.module

fun launchModule() = module {
    single<LaunchComponent> { (componentContext: ComponentContext, callbacks: LaunchNavigationCallbacks) ->
        DefaultLaunchComponent(
            componentContext = componentContext,
            loadUserUseCase = get(),
            loadCatalogUseCase = get(),
            loadCartUseCase = get(),
            callbacks = callbacks,
            snackBarManager = get()
        )
    }
}