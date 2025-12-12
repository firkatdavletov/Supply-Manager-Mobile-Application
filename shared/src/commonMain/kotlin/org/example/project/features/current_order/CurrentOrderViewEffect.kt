package org.example.project.features.current_order

import org.example.project.features.base.Reducer

sealed interface CurrentOrderViewEffect : Reducer.ViewEffect {
    data object None : CurrentOrderViewEffect
}