package org.example.project.data.mapper

import org.example.project.data.entities.BankInfoEntity
import org.example.project.domain.models.BankInfoModel
import kotlin.String

class BankInfoMapper {

    fun toModel(entity: BankInfoEntity) = BankInfoModel(
        bankName = entity.bankName,
        logoUrl = entity.logoUrl,
        schema = entity.schema,
        packageName = entity.packageName,
    )

    fun toModel(entities: List<BankInfoEntity>) = entities.map { toModel(it) }
}