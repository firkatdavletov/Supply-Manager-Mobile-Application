package org.example.project.features.main_tabs.orders

import org.example.project.domain.models.OrderModel
import org.example.project.features.base.Reducer

data class OrdersViewState(
    val isLoading: Boolean,
    val orders: List<OrderModel>
) : Reducer.ViewState
