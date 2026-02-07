package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.domain.repositories.CartRepository
import org.example.project.features.home.DefaultHomeComponent
import org.example.project.features.home.HomeCallbacks
import org.example.project.features.home.HomeComponent
import org.koin.core.module.Module
import org.koin.dsl.module

fun homeModule(): Module = module {
    single<HomeComponent> { (componentContext: ComponentContext, callbacks: HomeCallbacks) ->
        DefaultHomeComponent(
            componentContext = componentContext,
            snackBarManager = get(),
            homeCallbacks = callbacks,
            getOrdersUseCase = get(),
            orderRepository = get(),
        )
    }
}