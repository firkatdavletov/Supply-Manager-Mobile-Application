package org.example.project.features.search_address

import org.example.project.domain.models.DeliveryType
import org.example.project.features.base.Reducer

class SearchAddressReducer : Reducer<SearchAddressViewState, SearchAddressViewEvent, SearchAddressViewEffect> {
    override fun reduce(
        state: SearchAddressViewState,
        event: SearchAddressViewEvent
    ): SearchAddressViewState {
        return when (event) {
            is SearchAddressViewEvent.OnQueryChanged -> state.copy(
                query = event.query,
                addresses = emptyList(),
                isSearching = true,
            )
            is SearchAddressViewEvent.OnCartLoaded -> {
                state.copy(
                    isSearching = event.cart.deliveryType == DeliveryType.PICKUP,
                    deliveryType = event.cart.deliveryType
                )
            }
            is SearchAddressViewEvent.OnDepartmentsLoaded -> {
                state.copy(departments = event.departments)
            }
            is SearchAddressViewEvent.OnSearchComplete -> {
                state.copy(
                    isSearching = false,
                    addresses = event.addresses
                )
            }
            is SearchAddressViewEvent.OnAddressClicked -> {
                state.copy(isLoading = true)
            }
            SearchAddressViewEvent.OnBackClicked -> state
            is SearchAddressViewEvent.OnError -> state.copy(
                isSearching = false,
                isLoading = false
            )
            is SearchAddressViewEvent.OnThrowError -> state.copy(
                isLoading = false,
                isSearching = false
            )
            else -> state
        }
    }

    override fun handleEvent(event: SearchAddressViewEvent): SearchAddressViewEffect? {
        TODO("Not yet implemented")
    }
}