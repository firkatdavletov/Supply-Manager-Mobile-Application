package org.example.project.features.current_order

import org.example.project.domain.models.OrderModel
import org.example.project.features.base.Reducer

sealed interface CurrentOrderViewEvent : Reducer.ViewEvent {
    data object OnBackClicked: CurrentOrderViewEvent
    data class OnOrderLoaded(val order: OrderModel) : CurrentOrderViewEvent
}