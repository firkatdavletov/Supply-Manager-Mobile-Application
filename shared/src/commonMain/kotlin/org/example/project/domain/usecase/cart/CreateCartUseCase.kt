package org.example.project.domain.usecase.cart

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.base.IOUseCase

class CreateCartUseCase(
    private val cartRepository: CartRepository,
) : IOUseCase<CreateCartUseCase.Params, ResultModel<Boolean>>() {
    override fun execute(param: Params): Flow<ResultModel<Boolean>> {
        return cartRepository.createCart(
            deliveryType = param.deliveryType,
            deliveryAddress = param.deliveryAddress,
            departmentId = param.departmentId,
            deliveryInfo = param.deliveryInfoModel,
        )
    }

    class Params(
        val deliveryType: DeliveryType,
        val deliveryAddress: AddressModel?,
        val departmentId: Int,
        val deliveryInfoModel: DeliveryInfoModel?,
    ) {
        constructor(deliveryAddress: AddressModel, deliveryInfo: DeliveryInfoModel, departmentId: Int): this(
            deliveryType = DeliveryType.DELIVERY,
            deliveryAddress = deliveryAddress,
            departmentId = departmentId,
            deliveryInfoModel = deliveryInfo,
        )
        constructor(departmentId: Int): this(
            deliveryType = DeliveryType.PICKUP,
            deliveryAddress = null,
            departmentId = departmentId,
            deliveryInfoModel = null,
        )
    }
}