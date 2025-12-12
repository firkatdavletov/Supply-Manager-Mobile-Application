package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.current_order.CurrentOrderCallbacks
import org.example.project.features.current_order.CurrentOrderComponent
import org.example.project.features.current_order.DefaultCurrentOrderComponent
import org.koin.dsl.module

fun currentOrderModule() = module {
    factory <CurrentOrderComponent> { (componentContext: ComponentContext, callbacks: CurrentOrderCallbacks, orderId: Long) ->
        DefaultCurrentOrderComponent(componentContext, callbacks, get(), orderId, get())
    }
}