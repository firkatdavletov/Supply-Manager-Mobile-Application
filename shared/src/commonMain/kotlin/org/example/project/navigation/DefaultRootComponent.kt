package org.example.project.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import org.example.project.features.dialogs.product_card.ProductCardComponent
import org.example.project.features.SnackBarManager
import org.example.project.features.app_introduction.AppIntroCallbacks
import org.example.project.features.app_introduction.AppIntroductionComponent
import org.example.project.features.authorization.sign_in_component.SignInCallbacks
import org.example.project.features.authorization.sign_in_component.SignInComponent
import org.example.project.features.authorization.verification_component.VerificationComponent
import org.example.project.features.authorization.verification_component.VerifyCallbacks
import org.example.project.features.cart.CartComponent
import org.example.project.features.cart.CartViewCallbacks
import org.example.project.features.catalog.CatalogCallbacks
import org.example.project.features.catalog.CatalogComponent
import org.example.project.features.current_order.CurrentOrderCallbacks
import org.example.project.features.current_order.CurrentOrderComponent
import org.example.project.features.dialogs.delete_user_dialog.DeleteUserComponent
import org.example.project.features.dialogs.delete_user_dialog.DeleteUserDialogCallbacks
import org.example.project.features.dialogs.logout_user_dialog.LogoutUserComponent
import org.example.project.features.dialogs.logout_user_dialog.LogoutUserDialogCallbacks
import org.example.project.features.home.HomeCallbacks
import org.example.project.features.home.HomeComponent
import org.example.project.features.launch.LaunchComponent
import org.example.project.features.launch.LaunchNavigationCallbacks
import org.example.project.features.map.MapCallbacks
import org.example.project.features.map.MapComponent
import org.example.project.features.payment.PaymentCallbacks
import org.example.project.features.payment.PaymentComponent
import org.example.project.features.profile.ProfileCallbacks
import org.example.project.features.profile.ProfileComponent
import org.example.project.features.search_address.SearchAddressCallbacks
import org.example.project.features.search_address.SearchAddressComponent
import org.example.project.navigation.RootComponent.BottomChild.*
import org.example.project.navigation.RootComponent.Child.AppIntroduction
import org.example.project.navigation.RootComponent.Child.Cart
import org.example.project.navigation.RootComponent.Child.Catalog
import org.example.project.navigation.RootComponent.Child.CurrentOrder
import org.example.project.navigation.RootComponent.Child.Home
import org.example.project.navigation.RootComponent.Child.Launch
import org.example.project.navigation.RootComponent.Child.Payment
import org.example.project.navigation.RootComponent.Child.Profile
import org.example.project.navigation.RootComponent.Child.SearchAddress
import org.example.project.navigation.RootComponent.Child.SelectAddress
import org.example.project.navigation.RootComponent.Child.SignIn
import org.example.project.navigation.RootComponent.Child.Verification
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class DefaultRootComponent(
    componentContext: ComponentContext,
    override val snackBarManager: SnackBarManager,
) : RootComponent, ComponentContext by componentContext, KoinComponent {

    private val navigation  = StackNavigation<Config>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Launch,
        childFactory = ::createChild
    )

    override val dialogStack: Value<ChildSlot<*, RootComponent.BottomChild>> = childSlot(
        source = dialogNavigation,
        serializer = DialogConfig.serializer(),
        handleBackButton = true,
        childFactory = ::createBottomChild
    )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(toIndex)
    }

    override fun dismissDialog() {
        dialogNavigation.dismiss()
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is Config.AppIntroduction -> {
                AppIntroduction(getAppIntroComponent(componentContext))
            }
            is Config.Launch -> {
                Launch(getLaunchComponent(componentContext))
            }
            is Config.SelectAddress -> {
                SelectAddress(getMapComponent(componentContext, config))
            }
            is Config.Home -> {
                Home(getHomeComponent(componentContext, config))
            }
            is Config.Cart -> {
                Cart(getCartComponent(componentContext, config))
            }
            is Config.Payment -> {
                Payment(getPaymentComponent(componentContext))
            }

            is Config.CurrentOrder -> {
                CurrentOrder(getCurrentOrderComponent(componentContext, config.fromScreen, config.orderId))
            }

            is Config.Catalog -> {
                Catalog(getCatalogComponent(componentContext, config.categoryId, config.title))
            }

            Config.Profile -> {
                Profile(getProfileComponent(componentContext))
            }

            is Config.SignIn -> {
                SignIn(getSignInComponent(componentContext, config))
            }
            is Config.Verification -> {
                Verification(getVerificationComponent(componentContext, config))
            }

            is Config.SearchAddress -> {
                SearchAddress(getSearchAddressComponent(componentContext, config))
            }
        }
    }

    private fun createBottomChild(
        dialogConfig: DialogConfig,
        componentContext: ComponentContext
    ): RootComponent.BottomChild {
        return when (dialogConfig) {
            is DialogConfig.ProductCard -> {
                ProductCard(getProductCardComponent(componentContext, dialogConfig))
            }
            is DialogConfig.DeleteUser -> {
                DeleteUser(getDeleteUserComponent(componentContext, dialogConfig))
            }
            is DialogConfig.LogoutUser -> {
                LogoutUser(getLogoutUserComponent(componentContext, dialogConfig))
            }
        }
    }

    private fun getLaunchComponent(componentContext: ComponentContext): LaunchComponent {
        val callbacks = LaunchNavigationCallbacks(
            navigateToSelectAddress = { navigation.pushToFront(Config.SelectAddress(LaunchComponent::class.simpleName)) },
            navigateToHome = { navigation.pushToFront(Config.Home) }
        )

        return get { parametersOf(componentContext, callbacks) }
    }

    private fun getAppIntroComponent(componentContext: ComponentContext): AppIntroductionComponent {
        val callbacks = AppIntroCallbacks(
            navigateToAuth = {
                navigation.pushToFront(
                    Config.SignIn(AppIntroductionComponent::class.simpleName)
                ) }
        )
        return get { parametersOf(componentContext, callbacks) }
    }

    private fun getMapComponent(componentContent: ComponentContext, config: Config.SelectAddress): MapComponent {
        val callbacks = MapCallbacks(
            navigateBack = {
                navigation.pop()
            },
            navigateToSearchAddress = {
                navigation.pushToFront(Config.SearchAddress(MapComponent::class.simpleName))
            },
            navigateToHome = {
                navigation.pushToFront(Config.Home)
            },
            navigateToPayment = {
                navigation.pushToFront(Config.Payment)
            }
        )
        return get { parametersOf(componentContent, config.fromScreen, callbacks) }
    }

    private fun getHomeComponent(componentContent: ComponentContext, config: Config.Home): HomeComponent {
        val callbacks = HomeCallbacks(
            navigateToMap = {
                navigation.pushToFront(Config.SelectAddress(HomeComponent::class.simpleName))
            },
            navigateToCart = {
                navigation.pushToFront(Config.Cart)
            },
            navigateToCategory = { categoryId, title, ->
                navigation.pushNew(Config.Catalog(categoryId, title))
            },
            navigateToProfile = {
                navigation.pushToFront(Config.Profile)
            },
            navigateToOrder = {
                navigation.pushNew(Config.CurrentOrder(HomeComponent::class.simpleName, it))
            },
            navigateToAuthorization = {
                navigation.pushToFront(Config.SignIn(HomeComponent::class.simpleName))
            }
        )
        return get { parametersOf(componentContent, callbacks) }
    }

    private fun getCartComponent(componentContext: ComponentContext, config: Config.Cart): CartComponent {
        val callbacks = CartViewCallbacks(
            navigateToPayment = {
                navigation.pushToFront(Config.Payment)
            },
            onBackClicked = {
                navigation.pop()
            },
            navigateToLogin = {
                navigation.pushToFront(Config.SignIn(CartComponent::class.simpleName))
            }
        )
        return get { parametersOf(componentContext, config, callbacks) }
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun getPaymentComponent(componentContext: ComponentContext): PaymentComponent {
        val callbacks = PaymentCallbacks(
            navigateBack = {
                navigation.popWhile( { config ->
                    config != Config.Cart
                } )
            },
            navigateToOrder = { navigation.push(Config.CurrentOrder(PaymentComponent::class.simpleName, it)) },
            navigateToMap = { navigation.pushToFront(Config.SelectAddress(PaymentComponent::class.simpleName)) }
        )
        return get { parametersOf(componentContext, callbacks) }
    }

    private fun getCurrentOrderComponent(context: ComponentContext, fromScreen: String?, orderId: Long): CurrentOrderComponent {
        val callbacks = CurrentOrderCallbacks(
            navigateToBack = { navigation.pop() },
            navigateToHome = {
                navigation.pushToFront(Config.Home)
            }
        )
        return get {
            parametersOf(context, fromScreen, callbacks, orderId)
        }
    }

    private fun getCatalogComponent(context: ComponentContext, categoryId: Long, title: String): CatalogComponent {
        val callbacks = CatalogCallbacks(
            onBack = {
                navigation.pop()
            },
            onNavigateToCart = {
                navigation.pushToFront(Config.Cart)
            },
            showProductCard = {
                dialogNavigation.activate(DialogConfig.ProductCard(it))
            }
        )
        return get {
            parametersOf(context, categoryId, title, callbacks)
        }
    }

    private fun getProfileComponent(context: ComponentContext): ProfileComponent {
        val callbacks = ProfileCallbacks(
            navigateBack = {  navigation.pop() },
            showDeleteUserDialog = {
                dialogNavigation.activate(DialogConfig.DeleteUser)
            },
            showLogoutUserDialog = {
                dialogNavigation.activate(DialogConfig.LogoutUser)
            }
        )

        return get {
            parametersOf(context, callbacks)
        }
    }

    private fun getSignInComponent(componentContext: ComponentContext, config: Config.SignIn): SignInComponent {
        val callbacks = SignInCallbacks(
            navigateToHome = {
                navigation.pushToFront(Config.Home)
            },
            navigateToVerify = { phoneNumber, authType, fromScreen, checkId, callPhone ->
                navigation.pushNew(Config.Verification(fromScreen, phoneNumber, authType, checkId, callPhone))
            },
            onBack = {
                navigation.pop()
            }
        )
        return get {
            parametersOf(
                componentContext,
                config,
                callbacks
            )
        }
    }

    private fun getVerificationComponent(componentContext: ComponentContext, config: Config.Verification): VerificationComponent {
        val callbacks = VerifyCallbacks(
            onBack = {
                navigation.pop()
            },
            navigateToHome = {
                navigation.pushToFront(Config.Home)
            },
            navigateToPayment = {
                navigation.pushToFront(Config.Payment)
            }
        )
        return get {
            parametersOf(
                componentContext,
                config,
                callbacks,
            )
        }
    }

    private fun getSearchAddressComponent(componentContext: ComponentContext, config: Config.SearchAddress): SearchAddressComponent {
        val callbacks = SearchAddressCallbacks(
            navigateToHome = {
                navigation.pushToFront(Config.Home)
            },
            navigateBack = {
                navigation.pop()
            },
            navigateToMap = {
                navigation.pushToFront(Config.SelectAddress(fromScreen = it))
            },
            navigateToPayment = {
                navigation.pushToFront(Config.Payment)
            }
        )
        return get {
            parametersOf(
                componentContext,
                callbacks,
                config.fromScreen
            )
        }
    }

    private fun getProductCardComponent(componentContent: ComponentContext, dialogConfig: DialogConfig.ProductCard): ProductCardComponent {

        return get {
            parametersOf(componentContent, dialogConfig)
        }
    }

    private fun getLogoutUserComponent(componentContent: ComponentContext, dialogConfig: DialogConfig.LogoutUser): LogoutUserComponent {
        val callbacks = LogoutUserDialogCallbacks(
            onDismiss = {
                dialogNavigation.dismiss()
            },
            onSuccess = {
                dialogNavigation.dismiss()
                navigation.pop()
            }
        )
        return get {
            parametersOf(componentContent, dialogConfig, callbacks)
        }
    }

    private fun getDeleteUserComponent(componentContent: ComponentContext, dialogConfig: DialogConfig.DeleteUser): DeleteUserComponent {
        val callbacks = DeleteUserDialogCallbacks(
            onDismiss = {
                dialogNavigation.dismiss()
            },
            onSuccess = {
                dialogNavigation.dismiss()
                navigation.pop()
            }
        )
        return get {
            parametersOf(componentContent, dialogConfig, callbacks)
        }
    }
}