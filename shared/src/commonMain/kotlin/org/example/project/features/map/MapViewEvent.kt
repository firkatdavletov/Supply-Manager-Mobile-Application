package org.example.project.features.map

import org.example.project.domain.models.CartModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.DepartmentModel
import org.example.project.domain.models.GeoAddressModel
import org.example.project.features.base.Reducer

sealed interface MapViewEvent : Reducer.ViewEvent {
    data object OnConfirm : MapViewEvent
    data object OnBackClicked : MapViewEvent
    data object OnSearchAddressClicked : MapViewEvent
    data object OnLoading : MapViewEvent
    data class OnCartLoaded(val cartModel: CartModel) : MapViewEvent
    data class OnShowDepartments(val departments: List<DepartmentModel>) : MapViewEvent
    data class OnMoveToLocation(val latitude: Double, val longitude: Double) : MapViewEvent
    data class OnMapMoved(
        val latitude: Double,
        val longitude: Double,
        val reason: Int,
        val finished: Boolean,
    ) : MapViewEvent
    data class OnChangeDeliveryType(val type: DeliveryType) : MapViewEvent
    data class OnDepartmentSelected(val id: Int) : MapViewEvent
    data class OnFoundAddress(val address: GeoAddressModel) : MapViewEvent
    data class OnThrowError(val throwable: Throwable) : MapViewEvent
    data class OnError(val message: String?) : MapViewEvent
    data object OnDefaultError : MapViewEvent
    data class OnFindAddressError(val message: String) : MapViewEvent
}