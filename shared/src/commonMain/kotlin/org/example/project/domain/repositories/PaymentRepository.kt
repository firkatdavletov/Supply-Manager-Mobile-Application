package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.PaymentModel
import org.example.project.domain.models.PaymentTypeModel

interface PaymentRepository {
    fun getPaymentTypes(): Flow<List<PaymentTypeModel>>
    fun create(paymentType: String, amount: Double, cryptogram: String?, token: String?): Flow<PaymentModel>
}