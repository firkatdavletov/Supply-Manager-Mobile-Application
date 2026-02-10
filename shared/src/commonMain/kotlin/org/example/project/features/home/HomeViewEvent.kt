package org.example.project.features.home

import org.example.project.domain.models.OrderPreviewModel
import org.example.project.domain.models.UserModel
import org.example.project.features.base.Reducer

sealed interface HomeViewEvent : Reducer.ViewEvent {
    data class OnThrowError(val throwable: Throwable) : HomeViewEvent

    data class OnCurrentOrderLoaded(val orders: List<OrderPreviewModel>) : HomeViewEvent

    data class OnOrderClicked(val id: Long) : HomeViewEvent

    data object OnRefresh : HomeViewEvent

    data object OnAddClicked : HomeViewEvent

    data class OnUserLoaded(val user: UserModel) : HomeViewEvent

    data object OnUserClicked : HomeViewEvent
}