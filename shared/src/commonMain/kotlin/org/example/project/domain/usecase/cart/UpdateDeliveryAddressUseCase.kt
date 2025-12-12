package org.example.project.domain.usecase.cart

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.base.IOUseCase

class UpdateDeliveryAddressUseCase(
    private val cartRepository: CartRepository,
): IOUseCase<UpdateDeliveryAddressUseCase.Params, ResultModel<Boolean>>() {
    override fun execute(param: Params): Flow<ResultModel<Boolean>> {
        return cartRepository.updateDeliveryAddress(
            deliveryType = param.deliveryType,
            deliveryAddress = param.deliveryAddress,
            departmentId = param.departmentId,
            deliveryInfo = param.deliveryInfo,
            comment = param.comment,
        )
    }

    data class Params(
        val deliveryAddress: AddressModel? = null,
        val deliveryType: DeliveryType,
        val departmentId: Int,
        val deliveryInfo: DeliveryInfoModel,
        val comment: String? = null,
    )
}