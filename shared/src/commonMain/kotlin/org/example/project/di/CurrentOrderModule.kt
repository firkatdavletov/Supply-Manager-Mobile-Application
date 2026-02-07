package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.current_order.CurrentOrderCallbacks
import org.example.project.features.current_order.CurrentOrderComponent
import org.example.project.features.current_order.DefaultCurrentOrderComponent
import org.koin.dsl.module

fun currentOrderModule() = module {
    factory <CurrentOrderComponent> { (componentContext: ComponentContext, fromScreen: String?, callbacks: CurrentOrderCallbacks, orderId: Long) ->
        DefaultCurrentOrderComponent(
            componentContext = componentContext,
            fromScreen = fromScreen,
            callbacks = callbacks,
            orderId = orderId,
            getOrderByIdUseCase = get(),
            takeOrderUseCase = get(),
            completeOrderUseCase = get(),
            cancelOrderUseCase = get(),
            pendingOrderUseCase = get()
        )
    }
}