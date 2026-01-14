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

    HomeContent(
        modifier = Modifier
            .fillMaxSize()
//            .background(brush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.surfaceVariant)))
            .background(MaterialTheme.colorScheme.background),
        userName = state.userName,
        addressString = state.deliveryAddress,
        deliveryInfo = state.deliveryInfo,
        totalAmount = state.amount.toFloat(),
        currentOrders = state.currentOrders,
        onChangeAddressClicked = {
            component.onEvent(HomeViewEvent.OnAddressClicked)
        },
        onCategoryClicked = {
            component.onEvent(HomeViewEvent.OnCategoryClicked(it.id, it.title))
        },
        categories = state.categories,
        onAddToCart = {
            component.onEvent(HomeViewEvent.OnAddToCart(it))
        },
        onRemoveFromCart = {
            component.onEvent(HomeViewEvent.OnRemoveFromCart(it))
        },
        onCartButtonClicked = {
            component.onEvent(HomeViewEvent.OnCartButtonClicked)
        },
        onPersonClicked = {
            component.onEvent(HomeViewEvent.OnProfileClicked)
        },
        onOrderClicked = {
            component.onEvent(HomeViewEvent.OnOrderClicked(it))
        }
    )
}