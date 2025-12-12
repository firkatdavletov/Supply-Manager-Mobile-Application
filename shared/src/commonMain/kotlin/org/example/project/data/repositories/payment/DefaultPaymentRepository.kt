package org.example.project.data.repositories.payment

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.datastore.remote.payment.PaymentRemoteDataStore
import org.example.project.data.mapper.PaymentMapper
import org.example.project.domain.models.PaymentModel
import org.example.project.domain.models.PaymentTypeModel
import org.example.project.domain.repositories.PaymentRepository

class DefaultPaymentRepository(
    private val paymentRemoteDataStore: PaymentRemoteDataStore,
    private val paymentMapper: PaymentMapper,
): PaymentRepository {
    override fun getPaymentTypes(): Flow<List<PaymentTypeModel>> {
        return paymentRemoteDataStore.getPaymentTypes().map { paymentMapper.toModel(it) }
    }
    override fun create(paymentType: String, amount: Double, cryptogram: String?, token: String?): Flow<PaymentModel> {
        return paymentRemoteDataStore.create(paymentType, amount, cryptogram, token)
            .map { paymentMapper.toModel(it) }
    }
}