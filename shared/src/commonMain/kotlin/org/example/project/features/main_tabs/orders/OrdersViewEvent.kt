package org.example.project.features.main_tabs.orders

import org.example.project.domain.models.OrderModel
import org.example.project.features.base.Reducer

sealed interface OrdersViewEvent : Reducer.ViewEvent {
    data class OnThrowError(val throwable: Throwable) : OrdersViewEvent
    data class OnOrdersLoaded(val orders: List<OrderModel>) : OrdersViewEvent
}