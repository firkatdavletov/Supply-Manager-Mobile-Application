package org.example.project.feature.catalog_settings

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.catalog_settings.CatalogSettingsComponent
import org.example.project.features.catalog_settings.CatalogSettingsViewEvent

@Composable
fun CatalogSettingsScreen(component: CatalogSettingsComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(CatalogSettingsViewEvent.OnBackClicked)
    }

    CatalogSettingsContent(
        title = state.title,
        items = state.items,
        selectedItem = state.selectedItem,
        onBackClicked = {
            component.onEvent(CatalogSettingsViewEvent.OnBackClicked)
        },
        onItemClicked = { item ->
            component.onEvent(CatalogSettingsViewEvent.OnMenuItemClicked(item))
        },
    )
}
