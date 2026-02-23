package org.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import org.example.project.feature.app_introduction.AppIntroductionContent
import org.example.project.feature.authorization.AuthorizationContent
import org.example.project.feature.authorization.signIin.SignInScreen
import org.example.project.feature.authorization.verification.VerificationScreen
import org.example.project.feature.launch.LaunchScreen
import org.example.project.feature.main_tabs.MainTabsContent
import org.example.project.feature.cart.CartScreen
import org.example.project.feature.catalog.CatalogScreen
import org.example.project.feature.catalog_settings.CatalogSettingsScreen
import org.example.project.feature.categories_settings.CategoriesSettingsScreen
import org.example.project.feature.current_order.CurrentOrderScreen
import org.example.project.feature.edit_category.EditCategoryScreen
import org.example.project.feature.edit_product_setting.EditProductSettingScreen
import org.example.project.feature.home.HomeScreen
import org.example.project.feature.import_csv.ImportCsvScreen
import org.example.project.feature.map_view.MapScreen
import org.example.project.feature.payment.PaymentScreen
import org.example.project.feature.profile.ProfileScreen
import org.example.project.feature.products_settings.ProductsSettingsScreen
import org.example.project.feature.search_address.SearchAddressScreen
import org.example.project.feature.settings.SettingsScreen

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Authorization -> AuthorizationContent(child.component)
            is RootComponent.Child.AppIntroduction -> AppIntroductionContent(child.component)
            is RootComponent.Child.MainTabs -> MainTabsContent(child.component)
            is RootComponent.Child.Launch -> LaunchScreen(child.component)
            is RootComponent.Child.SelectAddress -> MapScreen(child.component)
            is RootComponent.Child.Home -> HomeScreen(child.component)
            is RootComponent.Child.Cart -> CartScreen(child.component)
            is RootComponent.Child.Payment -> PaymentScreen(child.component)
            is RootComponent.Child.CurrentOrder -> CurrentOrderScreen(child.component)
            is RootComponent.Child.Catalog -> CatalogScreen(child.component)
            is RootComponent.Child.CatalogSettings -> CatalogSettingsScreen(child.component)
            is RootComponent.Child.CategoriesSettings -> CategoriesSettingsScreen(child.component)
            is RootComponent.Child.EditCategory -> EditCategoryScreen(child.component)
            is RootComponent.Child.ProductsSettings -> ProductsSettingsScreen(child.component)
            is RootComponent.Child.EditProductSetting -> EditProductSettingScreen(child.component)
            is RootComponent.Child.ImportCsv -> ImportCsvScreen(child.component)
            is RootComponent.Child.Profile -> ProfileScreen(child.component)
            is RootComponent.Child.Settings -> SettingsScreen(child.component)
            is RootComponent.Child.SignIn -> SignInScreen(child.component)
            is RootComponent.Child.Verification -> VerificationScreen(child.component)
            is RootComponent.Child.SearchAddress -> SearchAddressScreen(child.component)
        }
    }
}
