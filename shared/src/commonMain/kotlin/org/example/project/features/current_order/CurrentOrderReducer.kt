package org.example.project.features.current_order

import org.example.project.domain.models.OrderStatus
import org.example.project.features.base.Reducer

class CurrentOrderReducer : Reducer<CurrentOrderViewState, CurrentOrderViewEvent, CurrentOrderViewEffect> {
    override fun reduce(
        state: CurrentOrderViewState,
        event: CurrentOrderViewEvent
    ): CurrentOrderViewState {
        return when (event) {
            is CurrentOrderViewEvent.OnOrderLoaded -> {
                state.copy(
                    number = event.order.id.toString(),
                    deliveryType = event.order.deliveryType,
                    addressString = event.order.deliveryAddress ?: "",
                    status = OrderStatus.getTitle(event.order.status),
                    items = event.order.items,
                    deliveryPrice = event.order.deliveryPrice.toInt(),
                    totalAmount = event.order.totalAmount.toInt(),
                    productsPrice = event.order.items.sumOf { it.price.toInt() },
                    comment = event.order.comment.orEmpty()
                )
            }

            else -> state
        }
    }

    override fun handleEvent(event: CurrentOrderViewEvent): CurrentOrderViewEffect? {
        TODO("Not yet implemented")
    }
}