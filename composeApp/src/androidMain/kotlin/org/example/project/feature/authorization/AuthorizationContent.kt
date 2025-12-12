package org.example.project.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import org.example.project.feature.authorization.sign_in.SignInScreen
import org.example.project.feature.authorization.verification.VerificationScreen
import org.example.project.features.authorization.AuthorizationComponent

@Composable
fun AuthorizationContent(
    component: AuthorizationComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is AuthorizationComponent.Child.SignInChild -> SignInScreen(child.component)
            is AuthorizationComponent.Child.VerificationChild -> VerificationScreen(child.component)
        }
    }
}