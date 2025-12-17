package org.example.project.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.SharedFlow
import org.example.project.features.DialogComponent
import org.example.project.features.SnackBarManager
import org.example.project.features.app_introduction.AppIntroductionComponent
import org.example.project.features.authorization.AuthorizationComponent
import org.example.project.features.authorization.sign_in_component.SignInComponent
import org.example.project.features.authorization.verification_component.VerificationComponent
import org.example.project.features.base.IosComponent
import org.example.project.features.launch.LaunchComponent
import org.example.project.features.main_tabs.MainTabsComponent
import org.example.project.features.home.HomeComponent
import org.example.project.features.cart.CartComponent
import org.example.project.features.catalog.CatalogComponent
import org.example.project.features.current_order.CurrentOrderComponent
import org.example.project.features.main_tabs.MainTabsComponent.Child
import org.example.project.features.map.MapComponent
import org.example.project.features.payment.PaymentComponent
import org.example.project.features.profile.ProfileComponent
import org.example.project.features.search_address.SearchAddressComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>
    val snackBarManager: SnackBarManager

    fun onBackClicked(toIndex: Int)

    sealed class DialogChild {
        class SimpleDialog(val component: DialogComponent): DialogChild()
    }

    sealed class Child {
        class Launch(val component: LaunchComponent): Child()
        class AppIntroduction(val component: AppIntroductionComponent) : Child()
        class Authorization(val component: AuthorizationComponent) : Child()
        class MainTabs(val component: MainTabsComponent): Child()
        class Home(val component: HomeComponent): Child()
        class SelectAddress(val component: MapComponent): Child()
        class Cart(val component: CartComponent) : Child()
        class Payment(val component: PaymentComponent) : Child()
        class CurrentOrder(val component: CurrentOrderComponent) : Child()
        class Catalog(val component: CatalogComponent) : Child()
        class Profile(val component: ProfileComponent) : Child()
        class SignIn(val component: SignInComponent) : Child()
        class Verification(val component: VerificationComponent) : Child()
        class SearchAddress(val component: SearchAddressComponent) : Child()
    }
}