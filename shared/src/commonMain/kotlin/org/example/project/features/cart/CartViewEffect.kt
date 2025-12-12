package org.example.project.features.cart

import org.example.project.features.base.Reducer

sealed interface CartViewEffect: Reducer.ViewEffect {
    data object None: CartViewEffect
}