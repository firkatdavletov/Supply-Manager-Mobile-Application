package org.example.project.features.payment

import org.example.project.features.base.Reducer

sealed interface PaymentViewEffect : Reducer.ViewEffect {
    data object None : PaymentViewEffect
}