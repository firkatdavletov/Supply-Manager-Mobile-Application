package org.example.project.feature.authorization.signIin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.authorization.signInComponent.SignInComponent
import org.example.project.features.authorization.signInComponent.SignInViewEvent

@Composable
fun SignInScreen(component: SignInComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(SignInViewEvent.OnBackClicked)
    }

    SignInContent(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        isLoading = state.isLoading,
        login = state.login,
        password = state.password,
        isLoginEnabled = state.confirmEnabled,
        onLoginChanged = {
            component.onEvent(SignInViewEvent.OnLoginChanged(it))
        },
        onPasswordChanged = {
            component.onEvent(SignInViewEvent.OnPasswordChanged(it))
        },
        onLoginClicked = {
            component.onEvent(SignInViewEvent.OnLoginClicked)
        },
    )
}
