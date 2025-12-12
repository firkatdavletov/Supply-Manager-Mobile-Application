package org.example.project.data.datastore.remote.payment

import kotlinx.coroutines.flow.Flow
import org.example.project.data.entities.PaymentEntity
import org.example.project.data.entities.PaymentTypeEntity

interface PaymentRemoteDataStore {
    fun getPaymentTypes(): Flow<List<PaymentTypeEntity>>
    fun create(paymentType: String, amount: Double, cryptogram: String?, token: String?): Flow<PaymentEntity>
}