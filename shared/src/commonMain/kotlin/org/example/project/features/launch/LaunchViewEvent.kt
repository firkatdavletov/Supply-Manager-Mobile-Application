package org.example.project.features.launch

import org.example.project.features.base.Reducer

sealed interface LaunchViewEvent: Reducer.ViewEvent {
    data object OnReconnect: LaunchViewEvent
    data object OnError: LaunchViewEvent
}