package org.example.project.di


import com.arkivanov.decompose.ComponentContext
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.payment.GetPaymentTypesUseCase
import org.example.project.features.payment.DefaultPaymentComponent
import org.example.project.features.payment.PaymentCallbacks
import org.example.project.features.payment.PaymentComponent
import org.koin.core.module.Module
import org.koin.dsl.module

fun paymentModule(): Module = module {
    single<PaymentComponent> { (componentContext: ComponentContext, callbacks: PaymentCallbacks) ->
        DefaultPaymentComponent(
            componentContext = componentContext,
            callbacks = callbacks,
            cartRepository = get<CartRepository>(),
            getPaymentTypesUseCase = get<GetPaymentTypesUseCase>(),
            createOrderUseCase = get(),
            updateCartAddressUseCase = get(),
            clearCartUseCase = get(),
            snackBarManager = get()
        )
    }
}