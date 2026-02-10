package org.example.project.features.payment

import org.example.project.features.base.Reducer

class PaymentReducer : Reducer<PaymentViewState, PaymentViewEvent, PaymentViewEffect> {
    override fun reduce(
        state: PaymentViewState,
        event: PaymentViewEvent,
    ): PaymentViewState {
        return when (event) {
            is PaymentViewEvent.OnCartChanged -> {
                val totalPrice = event.cart.totalPrice

                state.copy(
                    comment = event.cart.comment ?: "",
                    totalAmount = totalPrice,
                )
            }

            is PaymentViewEvent.OnPaymentTypesLoaded -> {
                state.copy(
                    isLoading = false,
                )
            }

            is PaymentViewEvent.OnChangeDeliveryType -> {
                state
            }

            is PaymentViewEvent.OnIsPrivateHouseChanged -> {
                state
            }

            is PaymentViewEvent.OnEntranceInputError -> {
                state
            }

            is PaymentViewEvent.OnFlatInputError -> {
                state
            }

            is PaymentViewEvent.OnEntranceChanged -> {
                event.value.toIntOrNull() ?: return state
                state
            }

            is PaymentViewEvent.OnFlatChanged -> {
                state
            }

            is PaymentViewEvent.OnCommentChanged -> {
                state.copy(
                    comment = event.value,
                )
            }

            else -> {
                state
            }
        }
    }

    override fun handleEvent(event: PaymentViewEvent): PaymentViewEffect? {
        TODO("Not yet implemented")
    }
}