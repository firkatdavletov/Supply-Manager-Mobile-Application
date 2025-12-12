package org.example.project.features.main_tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.example.project.features.cart.CartComponent
import org.example.project.features.cart.CartViewCallbacks
import org.example.project.features.main_tabs.MainTabsComponent.Child.*
import org.example.project.features.catalog.CatalogCallbacks
import org.example.project.features.catalog.CatalogComponent
import org.example.project.features.main_tabs.orders.OrdersCallbacks
import org.example.project.features.main_tabs.orders.OrdersComponent
import org.example.project.features.main_tabs.sbp_banks.SbpBanksCallbacks
import org.example.project.features.main_tabs.sbp_banks.SbpBanksComponent
import org.example.project.features.search_address.SearchAddressCallbacks
import org.example.project.features.search_address.SearchAddressComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DefaultMainTabsComponent(
    private val componentContext: ComponentContext,
    private val callback: MainTabsCallback
) : MainTabsComponent,
    ComponentContext by componentContext,
    KoinScopeComponent {

    private val navigation  = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, MainTabsComponent.Child>>
        get() = childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Map,
            key = KEY,
            childFactory = { config, context ->
                val childKey = when (config) {
                    is Config.Catalog -> "Catalog_${config.categoryId}"
                    is Config.Map -> "Map"
                    is Config.Cart -> "Cart"
                    is Config.SbpBanks -> "SbpBanks"
                    Config.Orders -> "Orders"
                }
                val scopedContext = context.childContext(childKey)
                createChild(config, scopedContext)
            }
        )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(toIndex)
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): MainTabsComponent.Child {
        return when (config) {
            Config.Cart -> {
                CartChild(getCartComponent(componentContext))
            }

            is Config.Map -> {
                TODO()
//                MapChild(getMapComponent(componentContext))
            }

            is Config.Catalog -> {
                CatalogChild(getCatalogComponent(componentContext, config.categoryId, config.title))
            }

            is Config.SbpBanks -> {
                SbpBanksChild(getSbpBanksComponent(componentContext, config.qrLink, config.canStoreToken))
            }

            Config.Orders -> {
                OrdersChild(getOrdersComponent(componentContext))
            }
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Map: Config()
        @Serializable
        data class Catalog @OptIn(ExperimentalUuidApi::class) constructor(
            val categoryId: Long,
            val title: String,
            val instanceId: String = Uuid.random().toString()
        ): Config()
        @Serializable
        data object Cart: Config()
        @Serializable
        data class SbpBanks(
            val qrLink: String,
            val canStoreToken: Boolean,
        ): Config()
        @Serializable
        data object Orders : Config()
    }

    override val scope: Scope by lazy { createScope(this)}


    private fun getCatalogComponent(context: ComponentContext, categoryId: Long, title: String): CatalogComponent {
        val callbacks = CatalogCallbacks(
            onBack = {
                navigation.pop()
            },
            onNavigateToCart = {
                navigation.pushNew(Config.Cart)
            }
        )
        return scope.get {
            parametersOf(context, categoryId, title, callbacks)
        }
    }

    private fun getCartComponent(context: ComponentContext): CartComponent {
        val callbacks = CartViewCallbacks(
            onBackClicked = {
                navigation.pop()
            },
            navigateToPayment = {

            },
            navigateToLogin = {

            }
        )
        return scope.get {
            parametersOf(context, callbacks)
        }
    }

    private fun getSbpBanksComponent(context: ComponentContext, qrLink: String, canStoreToken: Boolean): SbpBanksComponent {
        val callbacks = SbpBanksCallbacks(
            navigateToBack = { navigation.pop() }
        )

        return scope.get {
            parametersOf(context, callbacks, qrLink, canStoreToken)
        }
    }

    private fun getOrdersComponent(context: ComponentContext): OrdersComponent {
        val callbacks = OrdersCallbacks(
            navigateBack = { navigation.pop() },
            navigateToOrder = { }
        )
        return scope.get {
            parametersOf(context, callbacks)
        }
    }

    companion object {
        private const val KEY = "main_tabs_child_stack"
    }
}