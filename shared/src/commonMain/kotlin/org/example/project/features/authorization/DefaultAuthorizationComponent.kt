package org.example.project.features.authorization

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.example.project.features.authorization.sign_in_component.SignInCallbacks
import org.example.project.features.authorization.verification_component.VerifyCallbacks
import org.example.project.features.cart.CartComponent
import org.example.project.features.home.HomeComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class DefaultAuthorizationComponent(
    componentContext: ComponentContext,
    private val fromScreen: String?,
    private val callbacks: AuthNavCallbacks,
): AuthorizationComponent, ComponentContext by componentContext, KoinScopeComponent {

    private val navigation  = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, AuthorizationComponent.Child>>
        get() = childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.SignIn,
            key = KEY,
            childFactory = ::createChild
        )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(toIndex)
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): AuthorizationComponent.Child {
        return when (config) {
            is Config.SignIn -> {
                val callbacks = SignInCallbacks(
                    onBack = { navigation.pop() },
                    navigateToVerify = { phoneNumber, authType, _, _, _ ->
                        navigation.pushToFront(Config.Verification(phoneNumber, authType))
                    },
                    navigateToHome = { callbacks.navigateToHome() }
                )
                AuthorizationComponent.Child.SignInChild(
                    component = scope.get { parametersOf(
                        componentContext,
                        callbacks
                    ) }
                )
            }
            is Config.Verification -> {
                val callbacks = VerifyCallbacks(
                    onBack = { navigation.pop() },
                    navigateToHome = {

                    },
                    navigateToPayment = {

                    }
                )
                AuthorizationComponent.Child.VerificationChild(
                    component = scope.get {
                        parametersOf(
                            componentContext,
                            config.phoneNumber,
                            config.authType,
                            callbacks,
                        )
                    }
                )
            }
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object SignIn : Config()

        @Serializable
        data class Verification(
            val phoneNumber: String,
            val authType: String
        ) : Config()

    }

    override val scope: Scope by lazy { createScope(this)}

    companion object {
        private const val KEY = "auth_child_stack"
    }

}