package org.example.project.features.payment

import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.PaymentTypeModel
import org.example.project.features.base.Reducer

data class PaymentViewState(
    val isLoading: Boolean,
    val deliveryType: DeliveryType,
    val addressString: String?,
    val departmentName: String?,
    val isPrivateHome: Boolean,
    val entrance: String,
    val flat: String,
    val comment: String,
    val productPrice: Int,
    val deliveryPrice: Int,
    val totalAmount: Int,
    val paymentTypes: List<PaymentTypeModel>,
    val entranceInputError: String? = null,
    val flatInputError: String? = null,
) : Reducer.ViewState