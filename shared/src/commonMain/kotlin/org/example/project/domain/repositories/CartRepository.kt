package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import org.example.project.domain.models.ResultModel
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.CartModel
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.ProductModel

interface CartRepository {
    val cartSubject: SharedFlow<CartModel>
    fun loadCart(): Flow<ResultModel<Boolean>>
    fun createCart(
        deliveryType: DeliveryType,
        deliveryAddress: AddressModel?,
        departmentId: Int,
        deliveryInfo: DeliveryInfoModel?
    ): Flow<ResultModel<Boolean>>
    fun updateQuantity(product: ProductModel): Flow<ResultModel<Boolean>>
    fun updateDeliveryAddress(
        deliveryType: DeliveryType,
        deliveryAddress: AddressModel?,
        departmentId: Int,
        deliveryInfo: DeliveryInfoModel,
        comment: String?
    ): Flow<ResultModel<Boolean>>

    fun removeAll(): Flow<ResultModel<Boolean>>
}