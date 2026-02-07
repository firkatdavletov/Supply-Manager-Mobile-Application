package org.example.project.data.mapper

import org.example.project.data.entities.DeliveryInfoEntity
import org.example.project.domain.models.DeliveryInfoModel

class DeliveryInfoMapper {
    fun toModel(entity: DeliveryInfoEntity): DeliveryInfoModel {
        return DeliveryInfoModel(
            deliveryPrice = entity.deliveryPrice,
            freeDeliveryPrice = entity.freeDeliveryPrice ?: 0
        )
    }

    fun toEntity(model: DeliveryInfoModel): DeliveryInfoEntity {
        return DeliveryInfoEntity(
            deliveryPrice = model.deliveryPrice,
            freeDeliveryPrice = model.freeDeliveryPrice,
        )
    }
}