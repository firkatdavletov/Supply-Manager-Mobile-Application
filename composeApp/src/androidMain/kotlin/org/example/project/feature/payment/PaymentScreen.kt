package org.example.project.feature.payment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.payment.PaymentComponent
import org.example.project.features.payment.PaymentViewEvent

@Composable
fun PaymentScreen(component: PaymentComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(PaymentViewEvent.OnBackButtonClicked)
    }
}