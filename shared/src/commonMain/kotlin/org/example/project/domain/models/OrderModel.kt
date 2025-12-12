package org.example.project.domain.models

data class OrderModel(
    val id: Long,
    val status: OrderStatus,
    val items: List<OrderItemModel>,
    val deliveryPrice: Double,
    val totalAmount: Double,
    val deliveryType: DeliveryType,
    val deliveryAddress: String?,
    val comment: String?,
) {
    val statusTitle: String
        get() {
            return OrderStatus.getTitle(status)
        }
}