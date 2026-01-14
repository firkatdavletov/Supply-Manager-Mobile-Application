package org.example.project.features.main_tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import org.example.project.features.cart.CartComponent
import org.example.project.features.home.HomeComponent
import org.example.project.features.catalog.CatalogComponent
import org.example.project.features.main_tabs.sbp_banks.SbpBanksComponent
import org.example.project.features.search_address.SearchAddressComponent

interface MainTabsComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class HomeChild(val component: HomeComponent) : Child()
        class CatalogChild(val component: CatalogComponent) : Child()
        class CartChild(val component: CartComponent) : Child()
        class SbpBanksChild(val component: SbpBanksComponent) : Child()
        class SearchAddressChild(val component: SearchAddressComponent) : Child()
    }
}