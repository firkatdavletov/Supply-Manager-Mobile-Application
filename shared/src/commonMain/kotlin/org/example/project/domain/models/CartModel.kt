package org.example.project.domain.models

data class CartModel(
    val items: List<CartItemModel>,
    val deliveryType: DeliveryType,
    val deliveryAddress: AddressModel?,
    val deliveryInfo: DeliveryInfoModel,
    val totalPrice: Long,
    val department: DepartmentModel,
    val comment: String?,
)