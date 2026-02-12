package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.authorization.signInComponent.DefaultSignInComponent
import org.example.project.features.authorization.signInComponent.SignInCallbacks
import org.example.project.features.authorization.signInComponent.SignInComponent
import org.example.project.features.authorization.verification_component.DefaultVerificationComponent
import org.example.project.features.authorization.verification_component.VerificationComponent
import org.example.project.features.authorization.verification_component.VerifyCallbacks
import org.example.project.navigation.Config
import org.koin.dsl.module

fun authorizationModule() =
    module {
        factory<SignInComponent> { (componentContext: ComponentContext, _: Config.SignIn, callbacks: SignInCallbacks) ->
            DefaultSignInComponent(
                componentContext = componentContext,
                snackBarManager = get(),
                loginByEmailUseCase = get(),
                callbacks = callbacks,
            )
        }
        factory<VerificationComponent> { (componentContext: ComponentContext, config: Config.Verification, callbacks: VerifyCallbacks) ->
            DefaultVerificationComponent(
                componentContext = componentContext,
                snackBarManager = get(),
                verifyCodeUseCase = get(),
                phoneNumber = config.phoneNumber,
                authType = config.authType,
                callbacks = callbacks,
                fromScreen = config.fromScreen,
                checkId = config.checkId,
                callPhone = config.callPhone,
                loadUserUseCase = get(),
                orderRepository = get(),
                authRepository = get(),
            )
        }
    }
