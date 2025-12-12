package org.example.project.data.repositories.sbp_banks

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.example.project.data.api.payment_api.PaymentApi
import org.example.project.data.mapper.BankInfoMapper
import org.example.project.domain.models.BankInfoModel
import org.example.project.domain.repositories.SbpBanksRepository

class DefaultSbpBanksRepository(
    private val api: PaymentApi,
    private val mapper: BankInfoMapper,
) : SbpBanksRepository {
    override fun getBanks(canStoreToken: Boolean): Flow<List<BankInfoModel>> {
        return if (canStoreToken) {
            flow {
                emit(api.getSubBanks())
            }.map { mapper.toModel(it.banks) }
        } else {
            flow {
                emit(api.getQrBanks())
            }.map { mapper.toModel(it.banks) }
        }
    }
}