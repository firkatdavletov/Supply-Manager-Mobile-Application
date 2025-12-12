package org.example.project.features.main_tabs.orders

import org.example.project.features.base.Reducer

sealed interface OrdersViewEffect : Reducer.ViewEffect {
    data object None : OrdersViewEffect
}