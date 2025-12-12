package org.example.project.data.mapper

import org.example.project.data.entities.PaymentEntity
import org.example.project.data.entities.PaymentTypeEntity
import org.example.project.domain.models.PaymentModel
import org.example.project.domain.models.PaymentTypeModel

class PaymentMapper(
    private val bankInfoMapper: BankInfoMapper,
) {
    fun toModel(entity: PaymentTypeEntity) = PaymentTypeModel(
        id = entity.key,
        title = entity.title,
        selected = false,
    )

    fun toModel(entities: List<PaymentTypeEntity>) = entities.map { toModel(it) }

    fun toModel(entity: PaymentEntity) = PaymentModel(
        entity.success,
        entity.model?.qrUrl,
    )
}