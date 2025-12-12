package org.example.project.features.search_address

import org.example.project.domain.models.CartModel
import org.example.project.domain.models.DepartmentModel
import org.example.project.domain.models.GeoAddressModel
import org.example.project.features.base.Reducer

sealed interface SearchAddressViewEvent : Reducer.ViewEvent {
    data object OnBackClicked : SearchAddressViewEvent
    data object OnMapClicked : SearchAddressViewEvent
    data class OnQueryChanged(val query: String) : SearchAddressViewEvent
    data class OnCartLoaded(val cart: CartModel) : SearchAddressViewEvent
    data class OnDepartmentsLoaded(val departments: List<DepartmentModel>) : SearchAddressViewEvent
    data class OnSearchComplete(val addresses: List<GeoAddressModel>) : SearchAddressViewEvent
    data class OnAddressClicked(val address: GeoAddressModel) : SearchAddressViewEvent
    data class OnError(val message: String?) : SearchAddressViewEvent
    data class OnThrowError(val throwable: Throwable) : SearchAddressViewEvent
}