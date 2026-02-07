package org.example.project.features.current_order

import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.OrderItemModel
import org.example.project.domain.models.OrderStatus
import org.example.project.features.base.Reducer

data class CurrentOrderViewState(
    val companyName: String?,
    val customerName: String,
    val customerEmail: String,
    val customerPhone: String,
    val createdAt: String,
    val status: OrderStatus,
    val number: String,
    val deliveryType: DeliveryType,
    val addressString: String,
    val items: List<OrderItemModel>,
    val totalAmount: Long,
    val comment: String,
    val deliveryDate: String,
) : Reducer.ViewState
