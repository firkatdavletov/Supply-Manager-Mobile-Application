package org.example.project.feature.settings

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.settings.SettingsComponent
import org.example.project.features.settings.SettingsViewEvent

@Composable
fun SettingsScreen(component: SettingsComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(SettingsViewEvent.OnBackClicked)
    }

    SettingsContent(
        title = state.title,
        items = state.items,
        selectedItem = state.selectedItem,
        onBackClicked = {
            component.onEvent(SettingsViewEvent.OnBackClicked)
        },
        onItemClicked = { item ->
            component.onEvent(SettingsViewEvent.OnMenuItemClicked(item))
        },
    )
}
