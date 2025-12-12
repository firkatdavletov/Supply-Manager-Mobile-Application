package org.example.project.features.search_address

import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.DepartmentModel
import org.example.project.domain.models.GeoAddressModel
import org.example.project.features.base.Reducer

data class SearchAddressViewState(
    val deliveryType: DeliveryType,
    val query: String,
    val isLoading: Boolean,
    val isSearching: Boolean,
    val departments: List<DepartmentModel>,
    val addresses: List<GeoAddressModel>,
) : Reducer.ViewState
