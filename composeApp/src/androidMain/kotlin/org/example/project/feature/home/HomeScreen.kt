package org.example.project.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.home.DefaultHomeComponent
import org.example.project.features.home.HomeComponent
import org.example.project.features.home.HomeViewEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    val state by (component as DefaultHomeComponent).state.subscribeAsState()


}