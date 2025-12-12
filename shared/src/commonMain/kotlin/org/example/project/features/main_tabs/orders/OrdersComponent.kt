package org.example.project.features.main_tabs.orders

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.base.BaseComponent

abstract class OrdersComponent(
    componentContext: ComponentContext,
    initialState: OrdersViewState,
) : BaseComponent<OrdersViewState, OrdersViewEvent, OrdersViewEffect>(
    componentContext = componentContext,
    initialState = initialState,
    reducer = OrdersReducer(),
)
