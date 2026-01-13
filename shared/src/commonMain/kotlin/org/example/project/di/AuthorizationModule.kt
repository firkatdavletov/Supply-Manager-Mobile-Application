package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.authorization.sign_in_component.DefaultSignInComponent
import org.example.project.features.authorization.sign_in_component.SignInCallbacks
import org.example.project.features.authorization.sign_in_component.SignInComponent
import org.example.project.features.authorization.verification_component.DefaultVerificationComponent
import org.example.project.features.authorization.verification_component.VerificationComponent
import org.example.project.features.authorization.verification_component.VerifyCallbacks
import org.example.project.navigation.Config
import org.koin.dsl.module

fun authorizationModule() = module {
    single<SignInComponent> { (componentContext: ComponentContext, config: Config.SignIn, callbacks: SignInCallbacks) ->
        DefaultSignInComponent(
            componentContext = componentContext,
            snackBarManager = get(),
            getAuthTypesUseCase = get(),
            verifyPhoneNumberUseCase = get(),
            callbacks = callbacks,
            fromScreen = config.fromScreen,
        )
    }
    factory <VerificationComponent> { (componentContext: ComponentContext, config: Config.Verification, callbacks: VerifyCallbacks) ->
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