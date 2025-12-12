package org.example.project.features.map

import org.example.project.features.base.Reducer

sealed interface MapViewEffect: Reducer.ViewEffect {
    data class ShowError(val message: String): MapViewEffect
}