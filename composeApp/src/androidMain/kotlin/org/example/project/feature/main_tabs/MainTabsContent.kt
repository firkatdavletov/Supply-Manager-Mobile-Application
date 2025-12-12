package org.example.project.feature.main_tabs

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import org.example.project.feature.cart.CartScreen
import org.example.project.feature.catalog.CatalogContent
import org.example.project.feature.catalog.CatalogScreen
import org.example.project.feature.home.HomeScreen
import org.example.project.features.main_tabs.MainTabsComponent

@Composable
fun MainTabsContent(
    component: MainTabsComponent,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        contentWindowInsets = WindowInsets(0,0,0,0),
    ) {
        Children(
            stack = component.childStack,
            modifier = modifier.padding(it),
            animation = stackAnimation(fade())
        ) {
            when (val child = it.instance) {
                is MainTabsComponent.Child.HomeChild -> HomeScreen(child.component)
                is MainTabsComponent.Child.CatalogChild -> CatalogScreen(child.component)
                is MainTabsComponent.Child.CartChild -> CartScreen(child.component)
                is MainTabsComponent.Child.SbpBanksChild -> TODO()
                is MainTabsComponent.Child.OrdersChild -> TODO()
                is MainTabsComponent.Child.SearchAddressChild -> TODO()
            }
        }
    }
}