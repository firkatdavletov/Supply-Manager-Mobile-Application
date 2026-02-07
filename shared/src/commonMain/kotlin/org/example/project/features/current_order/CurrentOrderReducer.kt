package org.example.project.features.current_order

import org.example.project.domain.models.OrderStatus
import org.example.project.domain.utils.DateUtility
import org.example.project.features.base.Reducer

class CurrentOrderReducer : Reducer<CurrentOrderViewState, CurrentOrderViewEvent, CurrentOrderViewEffect> {
    override fun reduce(
        state: CurrentOrderViewState,
        event: CurrentOrderViewEvent
    ): CurrentOrderViewState {
        return when (event) {
            is CurrentOrderViewEvent.OnOrderLoaded -> {
                state.copy(
                    companyName = event.order.user.company,
                    customerName = event.order.user.name,
                    customerEmail = event.order.user.email,
                    customerPhone = event.order.user.phone,
                    createdAt = DateUtility.formatLocalDateTimeSimple(event.order.created),
                    number = event.order.id.toString(),
                    deliveryType = event.order.deliveryType,
                    addressString = event.order.deliveryAddress ?: "",
                    status = event.order.status,
                    items = event.order.items,
                    totalAmount = event.order.totalAmount,
                    comment = event.order.comment.orEmpty(),
                    deliveryDate = event.order.deliveryTime?.let {
                        DateUtility.formatLocalDateTimeSimple(it)
                    } ?: "-"
                )
            }

            else -> state
        }
    }

    override fun handleEvent(event: CurrentOrderViewEvent): CurrentOrderViewEffect? {
        TODO("Not yet implemented")
    }
}