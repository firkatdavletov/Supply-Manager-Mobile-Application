package org.example.project.features.payment

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.SnackBarManager
import org.example.project.features.base.BaseComponent

abstract class PaymentComponent(
    componentContext: ComponentContext,
    initialState: PaymentViewState,
    snackBarManager: SnackBarManager,
) : BaseComponent<PaymentViewState, PaymentViewEvent, PaymentViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = PaymentReducer(),
    snackBarManager = snackBarManager,
)
