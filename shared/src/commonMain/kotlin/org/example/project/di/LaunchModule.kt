package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.launch.DefaultLaunchComponent
import org.example.project.features.launch.LaunchComponent
import org.example.project.features.launch.LaunchNavigationCallbacks
import org.koin.dsl.module

fun launchModule() =
    module {
        factory<LaunchComponent> { (componentContext: ComponentContext, callbacks: LaunchNavigationCallbacks) ->
            DefaultLaunchComponent(
                componentContext = componentContext,
                loadUserUseCase = get(),
                callbacks = callbacks,
                snackBarManager = get(),
                orderRepository = get(),
            )
        }
    }