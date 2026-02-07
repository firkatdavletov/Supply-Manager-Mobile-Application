package org.example.project.feature.catalog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.catalog.CatalogComponent
import org.example.project.features.catalog.CatalogViewEvent

@Composable
fun CatalogScreen(component: CatalogComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(CatalogViewEvent.OnBackClicked)
    }


}