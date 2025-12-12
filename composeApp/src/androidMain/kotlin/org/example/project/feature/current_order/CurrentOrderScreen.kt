package org.example.project.feature.current_order

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.domain.models.DeliveryType
import org.example.project.features.current_order.CurrentOrderComponent
import org.example.project.features.current_order.CurrentOrderViewEvent
import org.example.project.features.payment.PaymentViewEvent

@Composable
fun CurrentOrderScreen(component: CurrentOrderComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(CurrentOrderViewEvent.OnBackClicked)
    }

    CurrentOrderContent(
        deliveryType = DeliveryType.DELIVERY,
        addressString = "ул. Щербакова 150/2б кв. 88",
        orderNumber = state.number,
        status = state.status,
        orderItems = state.items,
        productsPrice = state.productsPrice,
        deliveryPrice = state.deliveryPrice,
        totalPrice = state.deliveryPrice,
        comment = state.comment,
        onBackButtonClicked = {
            component.onEvent(CurrentOrderViewEvent.OnBackClicked)
        }
    )
}