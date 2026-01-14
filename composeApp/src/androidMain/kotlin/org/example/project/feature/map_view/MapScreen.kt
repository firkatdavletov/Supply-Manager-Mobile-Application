package org.example.project.feature.map_view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.map.DefaultMapComponent
import org.example.project.features.map.MapComponent
import org.example.project.features.map.MapViewEvent
import org.example.project.utils.GeolocationService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(component: MapComponent) {
    val context = LocalContext.current
    val state by (component as DefaultMapComponent).state.subscribeAsState()

    BackHandler {
        component.onEvent(MapViewEvent.OnBackClicked)
    }

    LocationPermissionRequest(
        onPermissionGranted = {
            GeolocationService.getCurrentLocationWithManager(context) { location ->
                if (location != null) {
                    component.onEvent(
                        MapViewEvent.OnMoveToLocation(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                }
            }
        }
    )

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { scaffoldPadding ->
        MapContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            addressString = state.deliveryAddress,
            deliveryInfo = state.deliveryInfo,
            isLoading = state.isLoading,
            isSearching = state.isSearching,
            isConfirmEnabled = state.confirmEnabled,
            location = state.currentPosition,
            moveToLocation = state.showLocation,
            deliveryType = state.deliveryType,
            onSelectDeliveryType = {
                component.onEvent(MapViewEvent.OnChangeDeliveryType(it))
            },
            onConfirm = {
                component.onEvent(MapViewEvent.OnConfirm)
            },
            onBackButtonClicked = {
                component.onEvent(MapViewEvent.OnBackClicked)
            },
            onMapMoved = { lat, lng, reason, finished ->
                component.onEvent(MapViewEvent.OnMapMoved(lat, lng, reason, finished))
            },
            onSelectDepartment = {
                component.onEvent(MapViewEvent.OnDepartmentSelected(it))
            },
            onRequestLocation = {
                GeolocationService.getCurrentLocationWithManager(context) { location ->
                    if (location != null) {
                        component.onEvent(
                            MapViewEvent.OnMoveToLocation(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        )
                    }
                }
            },
            onSearchClicked = {
                component.onEvent(MapViewEvent.OnSearchAddressClicked)
            },
            selectedDepartment = state.selectedDepartment,
            departments = state.departments,
            showBackButton = state.showBackButton
        )
    }
}
