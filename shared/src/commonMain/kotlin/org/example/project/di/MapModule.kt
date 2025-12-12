package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.map.DefaultMapComponent
import org.example.project.features.map.MapCallbacks
import org.example.project.features.map.MapComponent
import org.example.project.features.search_address.DefaultSearchAddressComponent
import org.example.project.features.search_address.SearchAddressCallbacks
import org.example.project.features.search_address.SearchAddressComponent
import org.koin.dsl.module

fun mapModule() = module {
    single<MapComponent> {  (componentContext: ComponentContext, fromScreen: String? , callbacks: MapCallbacks) ->
        DefaultMapComponent(
            componentContext = componentContext,
            snackBarManager = get(),
            callbacks = callbacks,
            getGeoAddressUseCase = get(),
            updateDeliveryAddressUseCase = get(),
            cartRepository = get(),
            getDepartmentsUseCase = get(),
            createCartUseCase = get(),
            loadCartUseCase = get(),
            fromScreen = fromScreen,
        )
    }

    factory<SearchAddressComponent> { (componentContext: ComponentContext, callbacks: SearchAddressCallbacks, fromScreen: String?) ->
        DefaultSearchAddressComponent(
            componentContext = componentContext,
            snackBarManager = get(),
            fromScreen = fromScreen,
            get(),
            get(),
            get(),
            get(),
            get(),
            callbacks,
        )
    }
}