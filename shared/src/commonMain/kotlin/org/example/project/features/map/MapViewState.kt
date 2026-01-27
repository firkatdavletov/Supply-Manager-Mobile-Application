package org.example.project.features.map

import org.example.project.domain.models.CityModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.DepartmentModel
import org.example.project.features.base.Reducer

data class MapViewState(
    val isLoading: Boolean,
    val isSearching: Boolean,
    val isError: Boolean,
    val deliveryType: DeliveryType,
    val deliveryAddress: String?,
    val city: CityModel?,
    val deliveryInfo: String?,
    val freeDeliveryPrice: Int?,
    val departments: List<DepartmentModel>,
    val selectedDepartment: Int?,
    val cartDepartment: DepartmentModel?,
    val currentPosition: UiPoint?,
    val confirmEnabled: Boolean,
    val showLocation: Boolean,
    val showBackButton: Boolean,
    val showSearchButton: Boolean,
    val errorMessage: String?
): Reducer.ViewState