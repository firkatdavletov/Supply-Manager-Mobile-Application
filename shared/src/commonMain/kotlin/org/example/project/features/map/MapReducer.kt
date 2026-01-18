package org.example.project.features.map

import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.DepartmentModel
import org.example.project.features.base.Reducer
import org.example.project.features.utils.DistanceCalculator

class MapReducer: Reducer<MapViewState, MapViewEvent, MapViewEffect> {
    override fun reduce(
        state: MapViewState,
        event: MapViewEvent,
    ): MapViewState {
        return when (event) {
            is MapViewEvent.OnCartLoaded -> {
                val cart = event.cartModel
                val newPosition = when (state.deliveryType) {
                    DeliveryType.PICKUP -> {
                        cart.department.let {
                            UiPoint(it.latitude, it.longitude)
                        }
                    }
                    DeliveryType.DELIVERY -> {
                        cart.deliveryAddress?.let {
                            UiPoint(it.latitude, it.longitude)
                        } ?: cart.department.let {
                            UiPoint(it.latitude, it.longitude)
                        }
                    }
                }
                state.copy(
                    isSearching = false,
                    isLoading = false,
                    deliveryType = cart.deliveryType,
                    cartDepartment = cart.department,
                    currentPosition = newPosition,
                    departments = state.departments.map {
                        it.copy(selected = it.id == cart.department.id)
                    },
                    showLocation = true,
                )
            }

            is MapViewEvent.OnMapMoved -> {
                state.copy(
                    isSearching = true,
                    deliveryAddress = if (state.deliveryType == DeliveryType.DELIVERY) null else state.deliveryAddress,
                    deliveryInfo = if (state.deliveryType == DeliveryType.DELIVERY) null else state.deliveryInfo,
                    isError = false,
                    showLocation = false,
                    currentPosition = UiPoint(event.latitude, event.longitude),
                    confirmEnabled = state.deliveryType == DeliveryType.PICKUP && state.selectedDepartment != null
                )
            }

            is MapViewEvent.OnShowDepartments -> state.copy(
                isLoading = false,
                departments = event.departments
            )

            is MapViewEvent.OnMoveToLocation -> state.copy(
                isSearching = true,
                isError = false,
                currentPosition = UiPoint(event.latitude, event.longitude),
                showLocation = true,
            )

            is MapViewEvent.OnChangeDeliveryType -> {
                val department = findClosestDepartment(
                    lat = state.currentPosition!!.latitude,
                    lon = state.currentPosition.longitude,
                    departments = state.departments
                )
                val currentPosition = if (event.type == DeliveryType.PICKUP) {
                    department?.let {
                        UiPoint(it.latitude, it.longitude)
                    } ?: state.currentPosition
                } else state.currentPosition
                val workTimeStr = department?.let {
                    buildString {
                        val workingHours = department.currentWorkingHours
                        if (department.isWorkingNow && workingHours != null) {
                            append("с ${workingHours.openTime} до ${workingHours.closeTime}")
                        } else {
                            append("Не работает")
                        }
                    }
                }
                state.copy(
                    isSearching = event.type == DeliveryType.DELIVERY,
                    isError = false,
                    deliveryAddress = if (event.type == DeliveryType.DELIVERY) null else department?.name,
                    deliveryInfo = if (event.type == DeliveryType.DELIVERY) null else workTimeStr,
                    deliveryType = event.type,
                    selectedDepartment = department?.id,
                    currentPosition = currentPosition,
                    confirmEnabled = department != null,
                    showLocation = true,
                )
            }

            is MapViewEvent.OnConfirm -> state.copy(
                isLoading = true
            )

            is MapViewEvent.OnDepartmentSelected -> {
                val department = state.departments.firstOrNull { it.id == event.id }
                if (department != null) {
                    if (department.isWorkingNow) {
                        department.currentWorkingHours
                    }
                    val workTimeStr = department.let {
                        buildString {
                            val workingHours = department.currentWorkingHours
                            if (department.isWorkingNow && workingHours != null) {
                                append("с ${workingHours.openTime} до ${workingHours.closeTime}")
                            } else {
                                append("Не работает")
                            }
                        }
                    }
                    state.copy(
                        showLocation = true,
                        deliveryAddress = department.name,
                        departments = state.departments.map {
                            it.copy(selected = it.id == event.id)
                        },
                        selectedDepartment = department.id,
                        deliveryInfo = workTimeStr,
                        currentPosition = UiPoint(department.latitude, department.longitude),
                        confirmEnabled = true,
                    )
                } else state
            }

            is MapViewEvent.OnFoundAddress -> {
                val street = event.address.street
                val house = event.address.house
                val entrance = event.address.entrance
                val deliveryPrice = event.address.deliveryInfo?.deliveryPrice
                val freeDeliveryPrice = event.address.deliveryInfo?.freeDeliveryPrice

                val addressString = buildString {
                    append(street)
                    append(", ")
                    append(house)
                    if (entrance != null) {
                        append(", ")
                        append(entrance)
                    }
                }

                val deliveryInfo = buildString {
                    append("Доставка ")
                    if (deliveryPrice != null && deliveryPrice > 0.0) {
                        append("${deliveryPrice.toInt()} руб")

                        if (freeDeliveryPrice != null) {
                            append("\n")
                            append("от ${freeDeliveryPrice.toInt()} рублей бесплатно")
                        }
                    } else {
                        append("бесплатно")
                    }
                }

                val point = UiPoint(event.address.latitude, event.address.longitude)

                state.copy(
                    isSearching = false,
                    isError = false,
                    currentPosition = point,
                    deliveryAddress = addressString,
                    deliveryInfo = deliveryInfo,
                    confirmEnabled = true,
                    city = event.address.city
                )
            }

            is MapViewEvent.OnThrowError -> {
                state.copy(
                    isLoading = false,
                )
            }

            is MapViewEvent.OnError -> {
                state.copy(
                    isLoading = false
                )
            }

            MapViewEvent.OnDefaultError -> {
                state.copy(
                    isLoading = false
                )
            }

            is MapViewEvent.OnFindAddressError -> {
                state.copy(
                    isSearching = false,
                    isError = true,
                    deliveryInfo = event.message,
                    confirmEnabled = false
                )
            }
            else -> state
        }
    }

    override fun handleEvent(event: MapViewEvent): MapViewEffect? {
        return when (event) {
            is MapViewEvent.OnThrowError -> MapViewEffect.ShowError(event.throwable.message ?: "Что-то пошло не так")
            is MapViewEvent.OnError -> MapViewEffect.ShowError(event.message ?: "Что-то пошло не так")
            else -> null
        }
    }

    private fun findClosestDepartment(
        lat: Double,
        lon: Double,
        departments: List<DepartmentModel>
    ): DepartmentModel? {
        return departments.minByOrNull { department ->
            DistanceCalculator.haversineDistance(lat, lon, department.latitude, department.longitude)
        }
    }
}