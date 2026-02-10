package org.example.project.features.payment

import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.PaymentTypeModel
import org.example.project.features.base.Reducer

data class PaymentViewState(
    val isLoading: Boolean,
    val contactName: String = "",
    val companyName: String = "",
    val email: String = "",
    val phone: String = "",
    val comment: String = "",
    val totalAmount: Long = 0,
) : Reducer.ViewState