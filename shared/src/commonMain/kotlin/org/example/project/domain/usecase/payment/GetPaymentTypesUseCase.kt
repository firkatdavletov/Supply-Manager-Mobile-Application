package org.example.project.domain.usecase.payment

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.PaymentTypeModel
import org.example.project.domain.repositories.PaymentRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetPaymentTypesUseCase(
    private val paymentRepository: PaymentRepository,
) : IOUseCase<Unit, List<PaymentTypeModel>>() {
    override fun execute(param: Unit): Flow<List<PaymentTypeModel>> {
        return paymentRepository.getPaymentTypes()
    }
}