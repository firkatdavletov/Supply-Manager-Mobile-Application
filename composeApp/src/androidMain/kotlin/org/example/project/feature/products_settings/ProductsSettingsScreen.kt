package org.example.project.feature.products_settings

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.products_settings.ProductsSettingsComponent
import org.example.project.features.products_settings.ProductsSettingsViewEvent

@Composable
fun ProductsSettingsScreen(component: ProductsSettingsComponent) {
    val state by component.state.subscribeAsState()
    val normalizedQuery = state.searchQuery.trim()
    val filteredProducts =
        if (normalizedQuery.isEmpty()) {
            state.products
        } else {
            state.products.filter { product ->
                product.title.contains(normalizedQuery, ignoreCase = true) ||
                    (product.description?.contains(normalizedQuery, ignoreCase = true) == true)
            }
        }

    BackHandler {
        component.onEvent(ProductsSettingsViewEvent.OnBackClicked)
    }

    ProductsSettingsContent(
        title = state.title,
        products = filteredProducts,
        searchQuery = state.searchQuery,
        selectedProductId = state.selectedProductId,
        isLoading = state.isLoading,
        onBackClicked = {
            component.onEvent(ProductsSettingsViewEvent.OnBackClicked)
        },
        onAddProductClicked = {
            component.onEvent(ProductsSettingsViewEvent.OnAddProductClicked)
        },
        onSearchQueryChanged = { value ->
            component.onEvent(ProductsSettingsViewEvent.OnSearchQueryChanged(value))
        },
        onProductClicked = { productId ->
            component.onEvent(ProductsSettingsViewEvent.OnProductClicked(productId))
        },
    )
}
