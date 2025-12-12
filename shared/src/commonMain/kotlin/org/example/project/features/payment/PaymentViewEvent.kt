package org.example.project.features.payment

import org.example.project.domain.models.CartModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.PaymentTypeModel
import org.example.project.features.base.Reducer

sealed interface PaymentViewEvent : Reducer.ViewEvent {
    data class OnCartChanged(val cart: CartModel) : PaymentViewEvent
    data class OnPaymentTypesLoaded(val paymentTypes: List<PaymentTypeModel>) : PaymentViewEvent
    data object OnBackButtonClicked : PaymentViewEvent
    data object OnConfirmButtonClicked : PaymentViewEvent
    data class OnChangeDeliveryType(val deliveryType: DeliveryType) : PaymentViewEvent
    data object OnChangeAddress : PaymentViewEvent
    data class OnIsPrivateHouseChanged(val isPrivateHouse: Boolean) : PaymentViewEvent
    data class OnEntranceInputError(val error: String) : PaymentViewEvent
    data class OnFlatInputError(val error: String) : PaymentViewEvent
    data class OnEntranceChanged(val value: String) : PaymentViewEvent
    data class OnFlatChanged(val value: String) : PaymentViewEvent
    data class OnCommentChanged(val value: String) : PaymentViewEvent
    data class OnError(val message: String?) : PaymentViewEvent
    data class OnThrowError(val throwable: Throwable) : PaymentViewEvent
}