package org.example.project.features.main_tabs.orders

import org.example.project.features.base.Reducer

class OrdersReducer : Reducer<OrdersViewState, OrdersViewEvent, OrdersViewEffect> {
    override fun reduce(
        state: OrdersViewState,
        event: OrdersViewEvent
    ): OrdersViewState {
        return when (event) {
            is OrdersViewEvent.OnOrdersLoaded -> state.copy(
                isLoading = false,
                orders = event.orders
            )
            is OrdersViewEvent.OnThrowError -> state.copy(
                isLoading = false
            )
        }
    }

    override fun handleEvent(event: OrdersViewEvent): OrdersViewEffect? {
        return when (event) {
            is OrdersViewEvent.OnOrdersLoaded -> null
            is OrdersViewEvent.OnThrowError -> null
        }
    }
}