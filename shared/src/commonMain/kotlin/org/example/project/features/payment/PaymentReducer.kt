package org.example.project.features.payment

import org.example.project.domain.utils.AddressUtility
import org.example.project.features.base.Reducer

class PaymentReducer : Reducer<PaymentViewState, PaymentViewEvent, PaymentViewEffect> {
    override fun reduce(
        state: PaymentViewState,
        event: PaymentViewEvent
    ): PaymentViewState {
        return when (event) {
            is PaymentViewEvent.OnCartChanged -> {
                val address = event.cart.deliveryAddress
                val department = event.cart.department
                val productPrice = event.cart.items.sumOf { it.price.toInt() * it.quantity }
                val deliveryPrice = event.cart.deliveryInfo.deliveryPrice.toInt()
                val totalPrice = event.cart.totalPrice.toInt()

                state.copy(
                    deliveryType = event.cart.deliveryType,
                    addressString = if (address != null) {
                        AddressUtility.makeAddressString(address)
                    } else {
                        null
                    },
                    departmentName = department.name,
                    isPrivateHome = false,
                    entrance = address?.entrance?.toString() ?: "",
                    flat = address?.flat ?: "",
                    comment = event.cart.comment ?: "",
                    productPrice = productPrice,
                    deliveryPrice = deliveryPrice,
                    totalAmount = totalPrice,
                    storeIsClosed = !department.isWorkingNow
                )
            }

            is PaymentViewEvent.OnPaymentTypesLoaded -> state.copy(
                isLoading = false,
                paymentTypes = event.paymentTypes
            )

            is PaymentViewEvent.OnChangeDeliveryType -> state.copy(
                deliveryType = event.deliveryType
            )

            is PaymentViewEvent.OnIsPrivateHouseChanged -> state.copy(
                isPrivateHome = event.isPrivateHouse
            )

            is PaymentViewEvent.OnEntranceInputError -> state.copy(
                entranceInputError = event.error
            )

            is PaymentViewEvent.OnFlatInputError -> state.copy(
                flatInputError = event.error
            )

            is PaymentViewEvent.OnEntranceChanged -> {
                event.value.toIntOrNull() ?: return state
                state.copy(
                    entrance = event.value,
                    entranceInputError = null,
                )
            }

            is PaymentViewEvent.OnFlatChanged -> state.copy(
                flat = event.value,
                flatInputError = null,
            )

            is PaymentViewEvent.OnCommentChanged -> state.copy(
                comment = event.value
            )

            else -> state
        }
    }

    override fun handleEvent(event: PaymentViewEvent): PaymentViewEffect? {
        TODO("Not yet implemented")
    }
}