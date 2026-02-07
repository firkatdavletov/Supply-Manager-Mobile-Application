package org.example.project.domain.models

data class OrderModel(
    val id: Long,
    val user: UserModel,
    val status: OrderStatus,
    val items: List<OrderItemModel>,
    val deliveryPrice: Long,
    val totalAmount: Long,
    val deliveryType: DeliveryType,
    val deliveryAddress: String?,
    val deliveryTime: String?,
    val comment: String?,
    val created: String,
    val modified: String,
)